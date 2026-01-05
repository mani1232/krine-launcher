@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/webview")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsDouble
import js.core.JsInt
import js.objects.JsPlainObject
import js.promise.Promise

@JsPlainObject
external interface WebviewOptions {
  val url: JsString?
  val x: JsInt
  val y: JsInt
  val width: JsInt
  val height: JsInt
  val transparent: JsBoolean?
  val focus: JsBoolean?
  val dragDropEnabled: JsBoolean?
  val proxyUrl: JsString?
  val zoomHotkeysEnabled: JsBoolean?
}

external fun getCurrentWebview(): Webview

external fun getAllWebviews(): Promise<JsArray<Webview>>

open external class Webview(window: Window, label: JsString, options: WebviewOptions) : JsAny {
  val label: JsString
  val window: Window

  companion object {
    fun getByLabel(label: JsString): Promise<Webview?>

    fun getCurrent(): Webview
  }

  fun <T : JsAny?> listen(event: JsString, handler: EventCallback<T>): Promise<UnlistenFn>

  fun <T : JsAny?> emit(event: JsString, payload: T? = definedExternally): Promise<JsAny?>

  fun show(): Promise<JsAny?>

  fun hide(): Promise<JsAny?>

  fun close(): Promise<JsAny?>

  fun setFocus(): Promise<JsAny?>

  fun setZoom(scaleFactor: JsDouble): Promise<JsAny?>

  fun setPosition(position: LogicalPosition): Promise<JsAny?> // TODO LogicalPosition | PhysicalPosition | Position

  fun setSize(size: LogicalSize): Promise<JsAny?> // TODO LogicalSize | PhysicalSize | Size

  fun reparent(window: WebviewWindow): Promise<JsAny?>
}
