plugins {
    alias(libs.plugins.setup.compose.library)
}

dependencies {
    api(projects.core.map.api)
    api(libs.yandex.maps)

    implementation(projects.core.uiKit)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.atomicfu)
}