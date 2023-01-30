@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutinesCore)

    implementation(libs.bundles.retrofit)

    implementation(libs.kotlin.serialization)

    testImplementation(libs.bundles.testing)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
