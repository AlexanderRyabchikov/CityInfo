enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
    }
}

listOf(
    ":core:base",
    ":core:cache",
    ":core:map:api",
    ":core:map:widgets",
    ":core:mvi",
    ":core:navigation",
    ":core:network",
    ":core:paging",
    ":core:ui-kit",
    ":core:utils"
).forEach { include(it) }

listOf(
    ":feature:city:api",
    ":feature:city:impl",
    ":feature:map:api",
    ":feature:map:impl"
).forEach { include(it) }

rootProject.name = "CityInfo"
include(":app")
 