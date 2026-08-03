@file:Suppress("DSL_SCOPE_VIOLATION")

import config.implementation
import org.gradle.kotlin.dsl.dependencies


plugins {
    id("compose-plugin")
}

dependencies {

    implementation(libs.orbit.mvi)
    implementation(libs.orbit.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}
