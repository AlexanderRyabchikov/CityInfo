package settings

import com.android.build.api.dsl.LibraryExtension
import config.implementation
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import config.projectImplementation

internal fun Project.featureUiSetup(
    extension: LibraryExtension,
) = extension.apply {

    dependencies {

        implementation(libs.koin.android)
        implementation(libs.koin.compose)
        implementation(libs.koin.navigation)

        implementation(libs.orbit.compose)

        implementation(libs.kotlin.coroutines)

        implementation(libs.kotlin.json)
        implementation(libs.androidx.compose.navigation)

        //ktor
        implementation(libs.ktor.core)
        implementation(libs.ktor.android)
        implementation(libs.ktor.okhttp)
        implementation(libs.ktor.logging)
        implementation(libs.ktor.json)
        implementation(libs.ktor.negotiation)
        
        projectImplementation(":core:ui-kit")
        projectImplementation(":core:base")
        projectImplementation(":core:mvi")
        projectImplementation(":core:network")
        projectImplementation(":core:navigation")
        projectImplementation(":core:paging")
    }
}
