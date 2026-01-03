import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(custom.plugins.kotlinMultiplatform)
    alias(custom.plugins.kotlinSerialization)
    alias(custom.plugins.ksp)
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    jvm {

    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(kotlin("reflect"))

            implementation(custom.serialization.protobuf)
            implementation(custom.kotlin.io)
        }
    }
}



tasks {
    val generateProto = register<JavaExec>("generateProto") {
        group = "documentation"
        description = "Generates .proto schema from KMP Data Classes"

        val jvmTarget = kotlin.targets["jvm"]
        val mainCompilation = jvmTarget.compilations["main"]

        classpath = files(
            mainCompilation.output.allOutputs,
            mainCompilation.runtimeDependencyFiles
        )

        mainClass.set("cc.worldmandia.krine_launcher.proto.ProtoGenerator")
    }

    named("wasmJsPackageJson") {
        dependsOn(generateProto)
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.krineProcessor)
    add("kspJvm", projects.krineProcessor)
    add("kspWasmJs", projects.krineProcessor)
}