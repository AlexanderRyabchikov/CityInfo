// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialx) apply false
}

tasks.register<Delete>("clean", fun Delete.() {
    delete = setOf(layout.buildDirectory.get().asFile)
})

buildscript {
    dependencies {
        classpath(libs.gradle.android)
    }
}