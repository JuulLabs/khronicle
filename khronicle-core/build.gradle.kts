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

dependencies {
    add("lintPublish", projects.khronicleAndroidLint)
}
