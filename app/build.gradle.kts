plugins {
    alias(libs.plugins.setup.android.application)
}

android {
    namespace = "cityinfo.io"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.android)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material2)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.navigation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(projects.core.uiKit)
    implementation(projects.core.navigation)
    implementation(projects.core.network)

    implementation(projects.feature.map.api)
    implementation(projects.feature.city.api)

    implementation(projects.feature.map.impl)
    implementation(projects.feature.city.impl)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}