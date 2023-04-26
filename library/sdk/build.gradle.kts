@file:Suppress("UnstableApiUsage")

import java.time.Year

@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.dokka.get().pluginId)
    id("de.mannodermaus.android-junit5")
    id("maven-publish")
}

android {
    namespace = "io.snabble.pay.sdk"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        @Suppress("DEPRECATION")
        targetSdk = libs.versions.sdk.target.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    api(project(":account"))
    api(project(":customerinfo"))
    api(project(":mandate"))
    api(project(":session"))
    api(project(":core"))
    api(project(":shared"))
    implementation(project(":api"))
    implementation(project(":network"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastorePreferences)
    implementation(libs.androidx.startupRuntime)

    implementation(libs.kotlin.serialization)

    implementation(libs.okhttp.loggingInterceptor)

    implementation(libs.bundles.koin)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.testing.android)

    androidTestRuntimeOnly(libs.test.junit5.androidTestRunner)
}

tasks.dokkaHtmlPartial {
    dokkaSourceSets {
        named("main") {
            moduleName.set("SnabblePay")
            includes.from("Module.md")
        }
    }

    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{
              "footerMessage": "© ${Year.now()} snabble GmbH"
            }"""
        )
    )
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
            create("sdk", MavenPublication::class.java) {
                from(components["release"])
                groupId = project.group.toString()
                artifactId = project.name
                version = project.extra.get("sdkVersion").toString()
            }
        }
    }
}
