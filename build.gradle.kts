plugins {
    id("repository-conventions")
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.api)
    alias(libs.plugins.dokka)
}

apiValidation {
    nonPublicMarkers.add("com.juul.khronicle.KhronicleInternal")
    ignoredProjects += "khronicle-android-lint"
}
