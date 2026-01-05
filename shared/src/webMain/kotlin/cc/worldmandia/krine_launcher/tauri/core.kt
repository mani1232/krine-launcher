@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/core")

package cc.worldmandia.krine_launcher.tauri

import js.buffer.ArrayBuffer
import js.core.JsInt
import js.objects.JsPlainObject
import js.promise.Promise
import js.promise.await
import web.http.Headers

@JsPlainObject
external interface InvokeOptions {
  val headers: Headers? // HeadersInit
}

open external class Resource(rid: Int) {
  val rid: JsInt

  fun close(): Promise<JsAny?>
}

external fun <T : ArrayBuffer?> invoke(
    cmd: JsString,
    args: ArrayBuffer? = definedExternally,
    options: InvokeOptions? = definedExternally,
): Promise<T>

external fun convertFileSrc(filePath: JsString, protocol: JsString? = definedExternally): JsString

external fun isTauri(): JsBoolean
