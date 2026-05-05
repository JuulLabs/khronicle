plugins {
    id("repository-conventions")
    id("test-conventions")
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinter)
}

kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    applyDefaultHierarchyTemplate()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.atomicfu)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
