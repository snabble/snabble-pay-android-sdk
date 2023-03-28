@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.benManesVersions)
    alias(libs.plugins.versionCatalogUpdate)
    alias(libs.plugins.dokka)
}

buildscript {
    dependencies {
        classpath(libs.classpath.kotlinGradlePlugin)
        classpath(libs.classpath.dokka.gradlePlugin)
        classpath(libs.classpath.dokka.base)
        classpath(libs.test.junit5.androidPlugin)
    }
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
    }

    detekt {
        config = files("${project.rootDir}/detekt.yml")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    ktlint {
        version.set("0.48.2")
        verbose.set(true)
        outputToConsole.set(true)
        coloredOutput.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        }
    }

    tasks.register("checkStyle") {
        dependsOn("detekt")
        dependsOn("ktlintCheck")
    }

    tasks.register("checkAll") {
        dependsOn("checkStyle")
        dependsOn(tasks.withType<Test>())
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

versionCatalogUpdate {
    sortByKey.set(false)
    keep {
        keepUnusedVersions.set(true)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA")
        .any { version.toUpperCase().contains(it) }
    val isNotStable = listOf("ALPHA", "BETA", "RC", "DEV")
        .any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not() && isNotStable
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}

tasks.dokkaHtmlMultiModule {
    outputDirectory.set(buildDir.resolve("snabble-pay-docs"))

    suppressInheritedMembers.set(true)

    includes.from("Module.md")

    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{
              "footerMessage": "© ${java.time.Year.now()} snabble GmbH"
            }"""
        )
    )
}
