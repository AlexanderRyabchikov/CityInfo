package settings

import com.android.build.api.dsl.LibraryExtension
import config.implementation
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.composeLibrarySetup(
    extension: LibraryExtension,
) = extension.apply {

    buildFeatures.compose = true

    dependencies {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.runtime)
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.tooling)
        implementation(libs.androidx.compose.foundation)
        implementation(libs.androidx.compose.material2)
        implementation(libs.androidx.compose.material3)
    }
}