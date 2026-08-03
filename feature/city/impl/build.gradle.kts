plugins {
    alias(libs.plugins.setup.feature.ui)
}

dependencies {
    implementation(projects.feature.city.api)
    implementation(projects.core.cache)
    implementation(projects.core.utils)

}