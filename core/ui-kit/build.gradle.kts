import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.setup.compose.library)
}

configure<LibraryExtension> {
    namespace = "cityinfo.io.core.uiKit"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.mvi)
    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}