plugins {
    alias(custom.plugins.kotlinMultiplatform)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation("com.google.devtools.ksp:symbol-processing-api:${custom.versions.ksp.get()}")
        }
    }
}
