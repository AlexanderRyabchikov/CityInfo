plugins {
    alias(libs.plugins.setup.feature.ui)
}

dependencies {
    api(projects.feature.map.api)
    api(projects.core.map.widgets)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.navigation)
    implementation(projects.core.utils)
}