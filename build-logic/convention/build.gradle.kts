import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.bundles.gradle.plugins)
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    // kotlin-stdlib исключён, чтобы не конфликтовать с версией, которой собирается kotlin-dsl
    implementation(libs.gradle.compose.screenshot) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
}


gradlePlugin {
    plugins {
        register("android-application-setup") {
            id = "android-application-setup"
            implementationClass = "AndroidApplicationPlugin"
            version = "1.0.0"
        }
    }
    plugins {
        register("android-library-setup") {
            id = "android-library-setup"
            implementationClass = "AndroidLibraryPlugin"
            version = "1.0.0"
        }
    }
    plugins {
        register("compose-library-setup") {
            id = "compose-library-setup"
            implementationClass = "ComposeLibraryPlugin"
            version = "1.0.0"
        }
    }
    plugins {
        register("feature-setup-api") {
            id = "feature-setup-api"
            implementationClass = "FeatureApiPlugin"
            version = "1.0.0"
        }
    }
    plugins {
        register("feature-setup-ui") {
            id = "feature-setup-ui"
            implementationClass = "FeatureUiPlugin"
            version = "1.0.0"
        }
    }
}
