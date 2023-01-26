plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.kotlinx.coroutinesCore)

    implementation(libs.bundles.testing)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
