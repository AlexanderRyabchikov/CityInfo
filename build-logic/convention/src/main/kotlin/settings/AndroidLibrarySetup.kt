package settings

import com.android.build.api.dsl.LibraryExtension
import config.ApkConfig
import config.commonAndroid
import config.configureLibraryBuildTypes
import config.implementation
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.androidLibrarySetup(
    extension: LibraryExtension,
) = extension.apply {

    val moduleName = path
        .drop(1)
        .replace("-", "_")
        .replace(":", ".")

    namespace = "${ApkConfig.APPLICATION_ID}.$moduleName"

    commonAndroid(project)
    configureLibraryBuildTypes()

    dependencies {
        implementation(libs.koin.core)
        implementation(libs.koin.android)
    }
}