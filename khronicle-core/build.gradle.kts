plugins {
    id("library-conventions")
    id("android-conventions")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(projects.khronicleTest)
        }
    }
}

android {
    dependencies {
        lintPublish(projects.khronicleAndroidLint)
    }
}
