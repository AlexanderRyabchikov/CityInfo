@file:Suppress("DSL_SCOPE_VIOLATION")

import com.android.build.api.dsl.LibraryExtension
import config.implementation
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("api-plugin")
    id("org.jetbrains.kotlin.plugin.compose")
}

configure<LibraryExtension> {
    buildFeatures.compose = true
}

dependencies {

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.constraints)
    implementation(libs.androidx.compose.material2)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.koin.compose)
}
