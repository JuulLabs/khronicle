buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.atomicfu)
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.android.publish) apply false
    alias(libs.plugins.api)
    alias(libs.plugins.dokka)
}

allprojects {
    group = "com.juul.khronicle"

    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test>().configureEach {
        testLogging {
            events("started", "passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showStackTraces = true
            showCauses = true
        }
    }

    withPluginWhenEvaluated("jacoco") {
        tasks.register<JacocoReport>("jacocoTestReport") {
            group = "Verification"
            description = "Generate JaCoCo test coverage report"

            reports {
                csv.required.set(false)
                html.required.set(true)
                xml.required.set(true)
            }

            classDirectories.setFrom(layout.buildDirectory.file("classes/atomicfu-orig/jvm/main"))
            sourceDirectories.setFrom(layout.projectDirectory.files("src/commonMain", "src/jvmMain"))
            executionData.setFrom(layout.buildDirectory.file("jacoco/jvmTest.exec"))

            dependsOn("jvmTest")
        }

        configure<JacocoPluginExtension> {
            toolVersion = libs.versions.jacoco.get()
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.fileProvider(layout.buildDirectory.file("dokkaHtmlMultiModule").map { it.asFile })
}

fun Project.withPluginWhenEvaluated(plugin: String, action: Project.() -> Unit) {
    pluginManager.withPlugin(plugin) { whenEvaluated(action) }
}

// `afterEvaluate` does nothing when the project is already in executed state, so we need a special check for this case.
fun <T> Project.whenEvaluated(action: Project.() -> T) {
    if (state.executed) {
        action()
    } else {
        afterEvaluate { action() }
    }
}
