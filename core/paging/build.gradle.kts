plugins {
    alias(libs.plugins.setup.compose.library)
}

dependencies {
    api(libs.paging.runtime)
    api(libs.paging.compose)

    implementation(libs.kotlin.coroutines)

    implementation(projects.core.network)
    implementation(projects.core.uiKit)
    implementation(projects.core.mvi)
}