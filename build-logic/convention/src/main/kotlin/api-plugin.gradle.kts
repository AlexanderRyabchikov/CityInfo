@file:Suppress("DSL_SCOPE_VIOLATION")

import config.implementation
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("plugin.serialization")
}

apply(plugin = "android-library-setup")

dependencies {
    implementation(libs.ktor.core)
    implementation(libs.kotlin.serialx)
    implementation(libs.kotlin.json)
}