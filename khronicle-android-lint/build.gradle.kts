plugins {
    id("kotlin-conventions")
    alias(libs.plugins.android.lint)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            compileOnly(libs.android.lint.api)
        }

        jvmTest.dependencies {
            implementation(libs.android.lint.api)
            implementation(libs.android.lint.test)
            implementation(libs.junit)
        }
    }
}
