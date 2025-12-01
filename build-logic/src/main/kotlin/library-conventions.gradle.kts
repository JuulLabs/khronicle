plugins {
    id("kotlin-conventions")
    jacoco
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
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
    }
}

val jacocoTestReport by tasks.registering(JacocoReport::class) {
    reports {
        csv.required.set(false)
        html.required.set(true)
        xml.required.set(true)
    }

    classDirectories.setFrom(file("${layout.buildDirectory}/classes/kotlin/jvm/main/"))
    sourceDirectories.setFrom(files("src/commonMain", "src/jvmMain"))
    executionData.setFrom(files("${layout.buildDirectory}/jacoco/jvmTest.exec"))

    dependsOn("jvmTest")
}
