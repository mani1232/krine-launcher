plugins {
    alias(custom.plugins.kotlinJvm)
    alias(custom.plugins.ktor)
    application
}

group = "cc.worldmandia.krine_launcher"
version = "1.0.0"

application {
    mainClass.set("cc.worldmandia.krine_launcher.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(custom.ktor.server.core)
    implementation(custom.ktor.server.cio)
}