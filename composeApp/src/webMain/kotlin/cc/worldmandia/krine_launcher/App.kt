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
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import krine_launcher.composeapp.generated.resources.Res
import krine_launcher.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalWasmJsInterop::class, ExperimentalSerializationApi::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()

    //val downloadState by EventApi.listenFlow<DownloadProgress>("download-progress")
    //    .collectAsStateWithLifecycle(DownloadProgress("", 0))
//
    //CoreApi.invoke<ArrayBuffer>("start_download")

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
                   // testString = test()
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
                    //Text("progress: ${downloadState.percent}")
                    //Text("fileName: ${downloadState.file}")
                }
            }
        }
    }
}