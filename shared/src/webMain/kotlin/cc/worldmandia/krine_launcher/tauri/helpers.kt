@file:OptIn(ExperimentalWasmJsInterop::class)

package cc.worldmandia.krine_launcher.tauri

import js.buffer.ArrayBuffer
import js.core.JsPrimitives.toKotlinString
import js.promise.await

// --- app ---

suspend fun getVersionSuspend() = getVersion().await().toKotlinString()

// --- core ---

suspend fun <T : ArrayBuffer?> invokeSuspend(
    cmd: String,
    args: ArrayBuffer? = null,
    options: InvokeOptions? = null,
): T = invoke<T>(cmd.toJsString(), args, options).await()

suspend fun Resource.closeSuspend() = close().await()

// --- event ---

typealias UnlistenFn = JsAny?

fun UnlistenFn.invoke(): Unit = invokeHelper(this)

private fun invokeHelper(f: UnlistenFn): Unit = js("f()")

typealias EventCallback<T> = (Event<T>) -> Unit

suspend fun <T : JsAny?> listenSuspend(event: String, handler: (Event<T>) -> Unit): UnlistenFn =
    listen(event.toJsString(), handler).await()

suspend fun <Payload : JsAny?> emitSuspend(
    event: String,
    payload: Payload? = null,
) = emit(event.toJsString(), payload).await()

// --- image ---

suspend fun Image.Companion.fromPathSuspend(path: String): Image = fromPath(path.toJsString()).await()

// --- path ---

@Suppress("UNUSED_PARAMETER")
object BaseDirectory {
    const val Audio = 1
    const val Cache = 2
    const val Config = 3
    const val Data = 4
    const val LocalData = 5
    const val Document = 6
    const val Download = 7
    const val Picture = 8
    const val Public = 9
    const val Video = 10
    const val Resource = 11
    const val Temp = 12
    const val AppConfig = 13
    const val AppData = 14
    const val AppLocalData = 15
    const val AppCache = 16
    const val AppLog = 17
    const val Desktop = 18
    const val Executable = 19
    const val Font = 20
    const val Home = 21
    const val Runtime = 22
    const val Template = 23
}

suspend fun joinSuspend(vararg paths: String) =
    join(*paths.map { it.toJsString() }.toTypedArray()).await()

suspend fun resourceDirSuspend() = resourceDir().await()?.toKotlinString()


suspend fun Window.requestUserAttention(type: UserAttentionType?) =
    _requestUserAttention(type?.value).await()

// --- window ---

suspend fun Window.theme(): Theme? = _theme().await()?.let { Theme(it) }

suspend fun Window.showSuspend() = show().await()

suspend fun Window.hideSuspend() = hide().await()


