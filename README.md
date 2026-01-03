# KRine launcher

[![Status](https://img.shields.io/badge/Status-In_dev-c72727.svg?logo=mavenCentral)]()
[![Kotlin](https://img.shields.io/badge/Kotlin_Multiplatform-2.3.0-278ec7.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose_Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-dev_builds-278ec7.svg?logo=jetpackcompose)](https://kotlinlang.org)
[![Rust](https://img.shields.io/badge/Rust-2024-278ec7.svg?logo=rust)](https://rust-lang.org/)

![Native](https://img.shields.io/badge/Native-ffffff)
![wasmJs](https://img.shields.io/badge/wasmJs-624FE8)

This is a WebView-based Minecraft launcher written in Kotlin/WasmJs using Compose Multiplatform and Rust/Tauri.

For backend part we use Kotlin/JVM with [Ktor framework](https://ktor.io/)

## Prerequisites

- [Gradle](https://gradle.org/releases/)
- [JDK 25](https://www.azul.com/downloads/?version=java-25-lts&os=windows&architecture=x86-64-bit&package=jdk#zulu)
- [NodeJs LTS](https://nodejs.org/en/download)
- [Tauri](https://tauri.app/start/prerequisites/)
- [protobuf](https://github.com/protocolbuffers/protobuf/releases/tag/v33.2)

## Getting Started

For build just frontend part

```groovy
gradle :composeApp:wasmJsBrowserDistribution
```

For start application in dev mode

```
cargo tauri dev
```

Build file for distribute

```
cargo tauri build
```

[Tauri distribute](https://tauri.app/distribute/)

## License

Code: (c) 2026 - [Apache License](LICENSE.txt)
