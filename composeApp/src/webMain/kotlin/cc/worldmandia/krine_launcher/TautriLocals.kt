package cc.worldmandia.krine_launcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTauriWindow =
    staticCompositionLocalOf<Window> {
      error("Tauri Window not provided. Wrap your content in TauriAppScope.")
    }

@Composable
fun TauriAppScope(content: @Composable () -> Unit) {
  val window = Window.getCurrent()

  CompositionLocalProvider(LocalTauriWindow provides window) { content() }
}
