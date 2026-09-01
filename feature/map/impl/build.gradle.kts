import com.android.build.api.dsl.LibraryExtension
import java.util.Properties

plugins {
    alias(libs.plugins.setup.feature.ui)
}

val secrets = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use(::load)
}

fun secret(name: String): String =
    secrets.getProperty(name) ?: System.getenv(name).orEmpty()

configure<LibraryExtension> {
    defaultConfig {
        buildConfigField("String", "YANDEX_MAP_KEY", "\"${secret("YANDEX_MAP_KEY")}\"")
    }
}

dependencies {
    api(projects.feature.map.api)
    api(projects.core.map.widgets)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.navigation)
    implementation(projects.core.utils)
}
