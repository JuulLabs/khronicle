plugins {
    kotlin("jvm")
    id("com.android.lint")
    id("com.vanniktech.maven.publish")
    id("org.jmailen.kotlinter")
}

dependencies {
    compileOnly(libs.android.lint.api)

    testImplementation(libs.android.lint.api)
    testImplementation(libs.android.lint.test)
    testImplementation(libs.junit)
}
