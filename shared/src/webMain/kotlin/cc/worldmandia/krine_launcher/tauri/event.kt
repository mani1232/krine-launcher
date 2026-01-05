@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/event")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsInt
import js.objects.JsPlainObject
import js.promise.Promise

external interface Event<T : JsAny?> {
  val event: JsString
  val id: JsInt
  val payload: T
}

@JsPlainObject
external interface EventOptions {
  val target: JsString?
}

external fun <T : JsAny?> listen(
    event: JsString,
    handler: EventCallback<T>,
    options: EventOptions? = definedExternally,
): Promise<UnlistenFn>

external fun <T : JsAny?> once(
    event: JsString,
    handler: EventCallback<T>,
    options: EventOptions? = definedExternally,
): Promise<UnlistenFn>

external fun <T : JsAny?> emit(event: JsString, payload: T? = definedExternally): Promise<JsAny?>

external fun <T : JsAny?> emitTo(
    target: JsAny,
    event: JsString,
    payload: T? = definedExternally,
): Promise<JsAny?>
