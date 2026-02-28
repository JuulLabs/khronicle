tasks.withType<Test>().configureEach {
    testLogging {
        events("started", "passed", "skipped", "failed", "standardOut", "standardError")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showStackTraces = true
        showCauses = true
    }
}
