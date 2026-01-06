package cc.worldmandia.krine_launcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.worldmandia.krine_launcher.proto.DownloadProgress
import cc.worldmandia.krine_launcher.proto.MinecraftData
import cc.worldmandia.krine_launcher.tauri.rememberTauriCommand
import cc.worldmandia.krine_launcher.tauri.rememberTauriEvent
import js.buffer.ArrayBuffer
import js.buffer.toArrayBuffer
import js.buffer.toByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import krine_launcher.composeapp.generated.resources.Res
import krine_launcher.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalSerializationApi::class)
val protoBuf = ProtoBuf {
    encodeDefaults = true
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalWasmJsInterop::class, ExperimentalSerializationApi::class)
@Composable
fun App() {
    val cmd = rememberTauriCommand<ArrayBuffer>("process_user_data")

    val progress by rememberTauriEvent<ArrayBuffer>("download-progress").state.collectAsStateWithLifecycle(null)

    val cmdStartDownload = rememberTauriCommand<ArrayBuffer>("start_download")

    MaterialExpressiveTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                showContent = !showContent
                cmd.execute(
                    protoBuf.encodeToByteArray(
                        MinecraftData(
                            "Windows",
                            "1.21.1"
                        )
                    ).toArrayBuffer()
                )
                cmdStartDownload.execute(null)
            }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")

                    if (cmd.isLoading.value) {
                        Text("Sending to Rust...")
                    }

                    cmd.data.value?.let { response ->
                        Text("Response: ${protoBuf.decodeFromByteArray<MinecraftData >(response.toByteArray())}")
                    }

                    progress?.let {
                        protoBuf.decodeFromByteArray<DownloadProgress>(it.payload.toByteArray())
                    }?.also {
                        Text("progress: ${it.percent}")
                        Text("fileName: ${it.file}")
                    }
                }
            }
        }
    }
}