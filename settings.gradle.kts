rootProject.name = "krine-launcher"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://packages.jetbrains.team/maven/p/firework/dev")
    maven("https://redirector.kotlinlang.org/maven/bootstrap")
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    maven("https://repo.worldmandia.cc/snapshots")
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://packages.jetbrains.team/maven/p/firework/dev")
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://redirector.kotlinlang.org/maven/bootstrap")
    maven("https://gitlab.com/api/v4/projects/38224197/packages/maven/")
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
  }

  versionCatalogs {
    create("kotlinWrappers") {
      val wrappersVersion = "2025.12.12"
      from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
    }
    create("custom") {
      from(files("gradle/custom.versions.toml"))

      providers.gradleProperty("compose-dev.version").orNull?.let {
        version("androidLifecycle", "2.10.0-alpha08+dev$it")
        version("navigation3", "1.1.0-alpha02+dev$it")
        version("materialAdaptive", "1.3.0-alpha04+dev$it")
        version("composeMultiplatform", "1.11.0-alpha02+dev$it")
      }
    }
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

include(":composeApp")

include(":server")

include(":shared")

include(":krine-processor")
