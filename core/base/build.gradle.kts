plugins {
    alias(libs.plugins.setup.android.library)
    alias(libs.plugins.setup.compose.library)
}

dependencies {
    api(libs.kotlin.coroutines)
    api(libs.koin.compose)
    api(libs.androidx.browser)

    api(projects.core.uiKit)
    api(projects.core.paging)
    api(projects.core.network)
    api(projects.core.mvi)
}