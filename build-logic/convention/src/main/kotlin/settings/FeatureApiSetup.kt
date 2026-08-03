package settings

import com.android.build.api.dsl.LibraryExtension
import config.implementation
import config.projectImplementation
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.featureApiSetup(
    extension: LibraryExtension,
) = extension.apply {

    dependencies {
        implementation(libs.kotlin.serialx)
        implementation(libs.kotlin.json)
        projectImplementation(":core:paging")
    }
}
