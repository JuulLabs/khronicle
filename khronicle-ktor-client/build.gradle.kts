plugins {
    id("library-conventions")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktor.core)
            api(libs.ktor.logging)
            api(projects.khronicleCore)
        }

        commonTest.dependencies {
            implementation(projects.khronicleTest)
            implementation(libs.ktor.mock)
        }
    }
}
