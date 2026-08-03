@file:Suppress("unused")

package settings

import com.android.build.api.dsl.ApplicationExtension
import config.ApkConfig
import config.commonAndroid
import config.configureAndroidBuildTypes
import config.implementation
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.androidApplicationSetup(
    extension: ApplicationExtension,
) = extension.apply {

    namespace = ApkConfig.APPLICATION_ID

    defaultConfig {
        versionCode = ApkConfig.VERSION_CODE
        versionName = ApkConfig.VERSION_NAME
        targetSdk = ApkConfig.TARGET_SDK_VERSION

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        vectorDrawables {
            useSupportLibrary = true
        }
    }

    lint.apply {
        disable += listOf("UnsafeExperimentalUsageError", "UnsafeExperimentalUsageWarning")
        checkReleaseBuilds = false
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
    }

    commonAndroid(project)
    configureAndroidBuildTypes()
    buildFeatures.compose = true

    dependencies {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.runtime)
        implementation(libs.androidx.compose.material2)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.tooling)

        implementation(libs.orbit.mvi)
        implementation(libs.orbit.compose)

        implementation(libs.koin.android)
    }
}