plugins {
    alias(libs.plugins.setup.compose.library)
}

dependencies {
    api(libs.orbit.mvi)
    api(libs.orbit.compose)

    implementation(libs.kotlin.coroutines)
}