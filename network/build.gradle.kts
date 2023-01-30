plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.7.20"

}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutinesCore)

    implementation(libs.bundles.testing)
    implementation(libs.bundles.retrofit)

    implementation(libs.kotlin.serialization)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
