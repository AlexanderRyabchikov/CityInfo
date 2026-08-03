plugins {
    alias(libs.plugins.setup.compose.library)
    alias(libs.plugins.kotlin.serialx)
}

dependencies {
    implementation(libs.kotlin.serialx)
    implementation(libs.kotlin.json)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.material.navigation)

    implementation(projects.core.uiKit)
}