import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    alias(libs.plugins.atomicfu)
    id("org.jmailen.kotlinter")
    jacoco
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()

    androidTarget().publishAllLibraryVariants()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    js().browser()
    jvm()
    macosArm64()
    macosX64()
    wasmJs().browser()

    sourceSets {
        all {
            languageSettings.optIn("com.juul.khronicle.KhronicleInternal")
        }

        androidMain.dependencies {
            // Workaround for AtomicFU plugin not automatically adding JVM dependency for Android.
            // https://github.com/Kotlin/kotlinx-atomicfu/issues/145
            implementation(libs.atomicfu)
        }

        androidUnitTest.dependencies {
            implementation(libs.androidx.test.junit)
            implementation(libs.androidx.test.runner)
            implementation(libs.robolectric)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(projects.khronicleTest)
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = 16

    namespace = "com.juul.khronicle"

    lint {
        abortOnError = true
        warningsAsErrors = true

        disable += "AndroidGradlePluginVersion"
        disable += "GradleDependency"
    }

    dependencies {
        lintPublish(projects.khronicleAndroidLint)
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.target.get()))
}
