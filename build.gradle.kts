@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
    }

    detekt {
        config = files("../detekt.yml")
    }

    ktlint {
        verbose.set(true)
        outputToConsole.set(true)
        coloredOutput.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
