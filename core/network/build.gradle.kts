import com.android.build.api.dsl.LibraryExtension
import java.util.Properties

plugins {
    alias(libs.plugins.setup.android.library)
    alias(libs.plugins.kotlin.serialx)
}

val secrets = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use(::load)
}

fun secret(name: String): String =
    secrets.getProperty(name) ?: System.getenv(name).orEmpty()

configure<LibraryExtension> {
    defaultConfig {
        buildConfigField("String", "API_BASE_URL", "\"${secret("API_BASE_URL")}\"")
    }
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
