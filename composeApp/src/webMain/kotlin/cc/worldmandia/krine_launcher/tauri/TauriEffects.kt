package cc.worldmandia.krine_launcher.tauri

import androidx.compose.runtime.*
import js.buffer.ArrayBuffer
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
fun <T : JsAny?> rememberTauriEvent(
    event: String,
    initial: T
): State<T> {
    val state = remember { mutableStateOf(initial) }

    LaunchedEffect(event) {
        val unlisten = listenSuspend(event) { eventObj ->
            state.value = eventObj.payload
        }
        try {
            awaitCancellation()
        } finally {
            unlisten?.invoke()
        }
    }

    return state
}

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
fun <Res : JsAny?> rememberTauriCommand(
    command: String,
    initial: Res? = null
): TauriCommandState<Res, ArrayBuffer> {
    val scope = rememberCoroutineScope()
    val data = remember { mutableStateOf(initial) }
    val isLoading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf<String?>(null) }

    fun execute(args: ArrayBuffer) {
        scope.launch {
            isLoading.value = true
            error.value = null
            try {
                val result: Res = invokeSuspend(command, args)
                data.value = result
            } catch (e: Throwable) {
                error.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    return TauriCommandState(
        data = data,
        isLoading = isLoading,
        error = error,
        execute = ::execute
    )
}

data class TauriCommandState<Res, Args>(
    val data: State<Res?>,
    val isLoading: State<Boolean>,
    val error: State<String?>,
    val execute: (Args) -> Unit
)