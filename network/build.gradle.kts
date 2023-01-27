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
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
