package cc.worldmandia.krine_launcher

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput

@OptIn(ExperimentalWasmJsInterop::class)
fun Modifier.tauriDraggable(window: Window): Modifier =
    this.pointerInput(Unit) {
      awaitPointerEventScope {
        while (true) {
          val event = awaitPointerEvent()
          if (event.type == PointerEventType.Press && event.buttons.isPrimaryPressed) {
            window.startDragging()
          }
        }
      }
    }

@Composable
fun Modifier.tauriDraggable(): Modifier {
  val window = LocalTauriWindow.current
  return this.tauriDraggable(window)
}
