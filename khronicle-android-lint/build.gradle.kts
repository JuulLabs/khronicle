plugins {
    id("repository-conventions")
    id("test-conventions")
    alias(libs.plugins.android.lint)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.maven.publish)
}

dependencies {
    compileOnly(libs.android.lint.api)

    testImplementation(libs.android.lint.api)
    testImplementation(libs.android.lint.test)
    testImplementation(libs.junit)
}
