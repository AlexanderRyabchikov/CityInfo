@file:Suppress("Filename", "RemoveSingleExpressionStringTemplate")

package config

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

fun CommonExtension.commonAndroid(target: Project) {
    configureDefaultConfig()
    configureBuildFeatures()
    configureCompileOptions()
    configureSigningConfig(target)
    target.suppressOptIn()
}

private fun CommonExtension.configureDefaultConfig() {
    compileSdk = ApkConfig.COMPILE_SDK_VERSION
    defaultConfig.minSdk = ApkConfig.MIN_SDK_VERSION

    packaging.apply {
        resources.excludes += "META-INF/LICENSE-LGPL-2.1.txt"
        resources.excludes += "META-INF/LICENSE-LGPL-3.txt"
        resources.excludes += "META-INF/LICENSE-W3C-TEST"
        resources.excludes += "META-INF/DEPENDENCIES"
        resources.excludes += "META-INF/versions/9/previous-compilation-data.bin"
    }
}

private fun CommonExtension.configureBuildFeatures() {
    buildFeatures.buildConfig = true
    buildFeatures.viewBinding = true
    buildFeatures.aidl = false
    buildFeatures.compose = false
    buildFeatures.prefab = false
    buildFeatures.resValues = false
    buildFeatures.shaders = false
}

private fun CommonExtension.configureCompileOptions() {
    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17
}

@Suppress("MaxLineLength")
private fun Project.suppressOptIn() {
    tasks.withType<KotlinCompile>()
        .configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
                freeCompilerArgs.addAll(
                    "-opt-in=com.google.accompanist.permissions.ExperimentalPermissionsApi",
                    "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.compose.ui.text.ExperimentalTextApi",
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                    "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlin.time.ExperimentalTime",
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                    "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                )
            }
        }
}

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.projectImplementation(depName: String) {
    add("implementation", (project(mapOf("path" to depName))))
}

fun DependencyHandler.projectApi(depName: String) {
    add("api", (project(mapOf("path" to depName))))
}

private fun CommonExtension.configureSigningConfig(target: Project) {

    val releaseSignFile = target.file("keystore/release_keystore.properties")
    val releaseSignProperties = Properties().apply {
        if (releaseSignFile.exists()) {
            load(FileInputStream(releaseSignFile))
        }
    }

    signingConfigs.create("debugCustom").apply {
        storeFile = target.file("keystore/debug.keystore")
        storePassword = "android"
        keyAlias = "androiddebugkey"
        keyPassword = "android"
    }

    signingConfigs.create("release").apply {
        storeFile = target.file("keystore/release.keystore")
        storePassword = releaseSignProperties.getProperty("storePassword")
        keyAlias = releaseSignProperties.getProperty("keyAlias")
        keyPassword = releaseSignProperties.getProperty("keyPassword")
    }
}

@Suppress("UNCHECKED_CAST")
private val ApplicationExtension.applicationBuildTypes: NamedDomainObjectContainer<ApplicationBuildType>
    get() = buildTypes as NamedDomainObjectContainer<ApplicationBuildType>

fun ApplicationExtension.configureAndroidBuildTypes() {
    applicationBuildTypes.getByName(BuildTypes.RELEASE.label) {
        isMinifyEnabled = true
        isShrinkResources = true
        isCrunchPngs = false

        buildConfigField("boolean", "isDebug", "false")

        applicationIdSuffix = BuildTypes.RELEASE.applicationIdSuffix
        signingConfig = signingConfigs.findByName("release")

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }

    applicationBuildTypes.getByName(BuildTypes.DEBUG.label) {
        isMinifyEnabled = false
        isDebuggable = true
        buildConfigField("boolean", "isDebug", "true")

        applicationIdSuffix = BuildTypes.DEBUG.applicationIdSuffix
        signingConfig = signingConfigs.findByName("debugCustom")
    }
}

fun LibraryExtension.configureLibraryBuildTypes() {
    buildTypes.getByName(BuildTypes.DEBUG.label)
        .buildConfigField("boolean", "isDebug", "true")
    buildTypes.getByName(BuildTypes.RELEASE.label)
        .buildConfigField("boolean", "isDebug", "false")
}