@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(libs.okhttp)

    implementation(libs.kotlinx.coroutinesCore)

    testImplementation(libs.bundles.testing)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

afterEvaluate {
    publishing {
        publications {
            repositories {
                maven {
                    name = "LocalBuildDir"
                    setUrl("file://${project.rootDir.absolutePath}/build/maven-releases/")
                }
            }
            create("network", MavenPublication::class.java) {
                from(components["java"])
                groupId = project.group.toString()
                artifactId = project.name
                version = project.extra.get("sdkVersion").toString()
            }
        }
    }
}
