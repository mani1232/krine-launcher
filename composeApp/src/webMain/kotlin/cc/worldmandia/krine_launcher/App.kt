package cc.worldmandia.krine_launcher

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.worldmandia.krine_launcher.proto.DownloadProgress
import cc.worldmandia.krine_launcher.tauri.CoreApi
import cc.worldmandia.krine_launcher.tauri.EventApi
import cc.worldmandia.krine_launcher.tauri.listenFlow
import cc.worldmandia.krine_launcher.tauri.protoBuf
import cc.worldmandia.krine_launcher.tauri.test
import js.buffer.ArrayBuffer
import js.buffer.toByteArray
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import org.jetbrains.compose.resources.painterResource

import krine_launcher.composeapp.generated.resources.Res
import krine_launcher.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalWasmJsInterop::class, ExperimentalSerializationApi::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()

    val downloadState by EventApi.listenFlow<DownloadProgress>("download-progress")
        .collectAsStateWithLifecycle(DownloadProgress("", 0))

    CoreApi.invoke<ArrayBuffer>("start_download")

    MaterialExpressiveTheme {
        var showContent by remember { mutableStateOf(false) }
        var testString by remember { mutableStateOf("empty") }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = {
                showContent = !showContent
                scope.launch {
                    testString = test()
                }
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
                    Text("testString: $testString")
                    Text("progress: ${downloadState.percent}")
                    Text("fileName: ${downloadState.file}")
                }
            }
        }
    }
}