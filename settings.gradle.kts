enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "khronicle"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

include(
    "khronicle-android",
    "khronicle-android-lint",
    "khronicle-core",
    "khronicle-ktor-client",
    "khronicle-test",
)
