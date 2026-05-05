enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "khronicle"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

includeBuild("build-logic")

include(
    "khronicle-android-lint",
    "khronicle-core",
    "khronicle-ktor-client",
    "khronicle-test",
)
