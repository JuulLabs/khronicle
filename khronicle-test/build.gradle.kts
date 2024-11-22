import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.atomicfu)
    id("org.jmailen.kotlinter")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()

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

        commonMain.dependencies {
            api(projects.khronicleCore)
        }
    }
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.target.get()))
}
