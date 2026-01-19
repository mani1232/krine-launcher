package cc.worldmandia.krine_launcher

import androidx.compose.runtime.*
import js.buffer.ArrayBuffer
import js.objects.unsafeJso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
fun <T : JsAny> rememberTauriEvent(event: String): TauriEventState<Event<T>> {
  val state = listenSuspend<T>(event)

  return TauriEventState(state)
}

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
fun <Result : JsAny> rememberTauriCommand(
    command: String,
    initial: Result? = null,
): TauriCommandState<Result, ArrayBuffer> {
  val scope = rememberCoroutineScope()
  val data = remember { mutableStateOf(initial) }
  val isLoading = remember { mutableStateOf(false) }
  val error = remember { mutableStateOf<String?>(null) }

  fun execute(args: ArrayBuffer? = null) {
    scope.launch {
      isLoading.value = true
      error.value = null
      try {
        val result: Result = invokeSuspend(command, unsafeJso<JsArrayBufferArg> { payload = args })
        data.value = result
      } catch (e: Throwable) {
        error.value = e.message ?: "Unknown error"
      } finally {
        isLoading.value = false
      }
    }
  }

  return TauriCommandState(data = data, isLoading = isLoading, error = error, execute = ::execute)
}

data class TauriCommandState<Res, Args>(
    val data: State<Res?>,
    val isLoading: State<Boolean>,
    val error: State<String?>,
    val execute: (Args?) -> Unit,
)

data class TauriEventState<T>(
    val state: Flow<T>,
)
