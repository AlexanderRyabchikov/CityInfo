plugins {
    alias(libs.plugins.setup.android.library)
    alias(libs.plugins.kotlin.serialx)
}

dependencies {
    api(libs.ktor.core)

    implementation(libs.ktor.android)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.negotiation)
    implementation(libs.ktor.json)

    implementation(libs.kotlin.json)
    implementation(libs.kotlin.coroutines)
}