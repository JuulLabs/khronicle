plugins {
    id("kotlin-conventions")
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget().publishLibraryVariants("debug", "release")

    sourceSets {
        androidUnitTest.dependencies {
            implementation(libs.androidx.test.junit)
            implementation(libs.androidx.test.runner)
            implementation(libs.robolectric)
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = libs.versions.android.min.get().toInt()

    namespace = "com.juul.khronicle.${project.name.replace("-", ".")}"

    lint {
        abortOnError = true
        warningsAsErrors = true

        disable += "AndroidGradlePluginVersion"
        disable += "GradleDependency"
    }
}
