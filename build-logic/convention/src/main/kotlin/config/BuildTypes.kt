package config

enum class BuildTypes(
    val applicationIdSuffix: String? = null,
    val label: String,
) {
    DEBUG(applicationIdSuffix = null, label = "debug"),
    RELEASE(applicationIdSuffix = null, label = "release"),
}