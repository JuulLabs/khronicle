plugins {
    id("kotlin-conventions")
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    android {
        compileSdk = libs.versions.android.compile.get().toInt()
        minSdk = libs.versions.android.min.get().toInt()

        namespace = "com.juul.khronicle.${project.name.replace("-", ".")}"

        withHostTest {}

        lint {
            abortOnError = true
            warningsAsErrors = true

            disable += "AndroidGradlePluginVersion"
            disable += "GradleDependency"
        }
    }

    sourceSets {
        named("androidHostTest") {
            dependencies {
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.runner)
                implementation(libs.robolectric)
            }
        }
    }
}
