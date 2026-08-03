import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.setup.feature.api)
}

configure<LibraryExtension> {
    namespace = "cityinfo.io.feature.city.api"
}