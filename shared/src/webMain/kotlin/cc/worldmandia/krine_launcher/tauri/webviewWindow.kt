@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/webviewWindow")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsInt
import js.objects.JsPlainObject
import js.promise.Promise

@JsPlainObject
external interface WebviewWindowOptions {
  val url: JsString?
  val title: JsString?
  val width: JsInt?
  val height: JsInt?
  val x: JsInt?
  val y: JsInt?
  val transparent: JsBoolean?
  val fullscreen: JsBoolean?
  val focus: JsBoolean?
  val resizable: JsBoolean?
  val decorations: JsBoolean?
  val alwaysOnTop: JsBoolean?
}

external fun getCurrentWebviewWindow(): WebviewWindow

external fun getAllWebviewWindows(): Promise<JsArray<WebviewWindow>>

external class WebviewWindow(label: String, options: WebviewWindowOptions? = definedExternally) :
    Webview {

  companion object {
    fun getByLabel(label: JsString): Promise<WebviewWindow?>

    fun getCurrent(): WebviewWindow
  }

  fun setTitle(title: String): Promise<JsAny?>

  fun maximize(): Promise<JsAny?>

  fun unmaximize(): Promise<JsAny?>

  fun minimize(): Promise<JsAny?>

  fun unminimize(): Promise<JsAny?>

  fun setResizable(resizable: JsBoolean): Promise<JsAny?>

  fun setDecorations(decorations: JsBoolean): Promise<JsAny?>

  fun setAlwaysOnTop(alwaysOnTop: JsBoolean): Promise<JsAny?>

  fun center(): Promise<JsAny?>

  @JsName("requestUserAttention") fun _requestUserAttention(requestType: JsInt?): Promise<JsAny?>
}
