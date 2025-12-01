enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "khronicle"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

includeBuild("build-logic")

include(
    "khronicle-android-lint",
    "khronicle-core",
    "khronicle-ktor-client",
    "khronicle-test",
)
