import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(custom.plugins.kotlinMultiplatform)
    alias(custom.plugins.composeMultiplatform)
    alias(custom.plugins.composeCompiler)
    alias(custom.plugins.kotlinSerialization)
    kotlin("plugin.atomicfu") version custom.versions.kotlin
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                devServer?.open = false // You can enable it for browser
            }
        }
        generateTypeScriptDefinitions()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(custom.bundles.compose.full)
            implementation(custom.serialization.protobuf)
            implementation(kotlinWrappers.browser)
            implementation(kotlinWrappers.js)
            implementation(projects.shared)
        }
    }
}