plugins {
    kotlin("multiplatform")
    id("kotlinx-atomicfu")
    id("org.jmailen.kotlinter")
    jacoco
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

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
