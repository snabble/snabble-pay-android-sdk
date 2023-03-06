@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION") plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
    id("de.mannodermaus.android-junit5")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "io.snabble.pay.app"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "io.snabble.pay.app"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation(libs.androidx.core)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.constraintlayoutCompose)
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("com.google.zxing:core:3.5.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:javase:3.5.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-util")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation("io.github.raamcosta.compose-destinations:core:1.8.35-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.35-beta")

    // DI
    implementation(libs.dagger.hilt.android)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.kotlin.serialization)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestRuntimeOnly(libs.test.junit5.androidTestRunner)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

android.applicationVariants.all {
    kotlin.sourceSets {
        getByName(name) {
            kotlin.srcDir("build/generated/ksp/$name/kotlin")
        }
    }
}

android.applicationVariants.all {
    addJavaSourceFoldersToModel(
        File(buildDir, "generated/ksp/$name/kotlin")
    )
}
