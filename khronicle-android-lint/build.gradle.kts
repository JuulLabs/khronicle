plugins {
    id("com.android.lint")
    kotlin("jvm")
    id("org.jmailen.kotlinter")
}

dependencies {
    compileOnly(libs.android.lint.api)

    testImplementation(libs.android.lint.api)
    testImplementation(libs.android.lint.test)
    testImplementation(libs.junit)
}
