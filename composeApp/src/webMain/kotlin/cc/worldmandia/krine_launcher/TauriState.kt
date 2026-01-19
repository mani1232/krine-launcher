@file:OptIn(ExperimentalWasmJsInterop::class)

package cc.worldmandia.krine_launcher

import androidx.compose.runtime.*
import js.core.JsPrimitives.toKotlinString
import js.promise.await
import kotlinx.coroutines.awaitCancellation

@Composable
fun rememberTauriTheme(): State<String?> {
  val window = LocalTauriWindow.current
  val theme = remember { mutableStateOf<String?>(null) }

  LaunchedEffect(Unit) {
    theme.value = window.theme()?.value?.toKotlinString()

    val unlisten =
        window
            .listen<JsString>("tauri://theme-changed".toJsString()) { newTheme ->
              theme.value = newTheme.payload.toKotlinString()
            }
            .await()

    try {
      awaitCancellation()
    } finally {
      unlisten()
    }
  }

  return theme
}
