@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/window")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsDouble
import js.core.JsInt
import js.core.JsPrimitives.toKotlinString
import js.objects.JsPlainObject
import js.promise.Promise
import js.promise.await

@JsPlainObject
external interface WindowOptions {
  val title: JsString?
  val width: JsInt?
  val height: JsInt?
  val x: JsInt?
  val y: JsInt?
  val resizable: JsBoolean?
  val fullscreen: JsBoolean?
  val focus: JsBoolean?
  val transparent: JsBoolean?
  val maximized: JsBoolean?
  val visible: JsBoolean?
  val decorations: JsBoolean?
  val alwaysOnTop: JsBoolean?
  val skipTaskbar: JsBoolean?
  val theme: JsString?
  val titleBarStyle: JsString?
  val hiddenTitle: JsBoolean?
  val maximizable: JsBoolean?
  val minimizable: JsBoolean?
  val closable: JsBoolean?
  val parent: JsAny?
  val visibleOnAllWorkspaces: JsBoolean?
}

external fun getCurrentWindow(): Window

external fun getAllWindows(): Promise<JsArray<Window>>

open external class Window(label: JsString, options: WindowOptions? = definedExternally) : JsAny {
  val label: JsString

  companion object {
    fun getByLabel(label: String): Promise<Window?>

    fun getCurrent(): Window
  }

  // События
  fun <T : JsAny?> listen(event: JsString, handler: EventCallback<T>): Promise<UnlistenFn>

  fun <T : JsAny?> once(event: JsString, handler: EventCallback<T>): Promise<UnlistenFn>

  fun <T : JsAny?> emit(event: JsString, payload: T? = definedExternally): Promise<JsAny?>

  fun scaleFactor(): Promise<JsDouble>

  fun innerPosition(): Promise<PhysicalPosition>

  fun outerPosition(): Promise<PhysicalPosition>

  fun innerSize(): Promise<PhysicalSize>

  fun outerSize(): Promise<PhysicalSize>

  fun isFullscreen(): Promise<JsBoolean>

  fun isMaximized(): Promise<JsBoolean>

  fun isMinimized(): Promise<JsBoolean>

  fun isFocused(): Promise<JsBoolean>

  fun isVisible(): Promise<JsBoolean>

  fun title(): Promise<JsString>

  // Внутренние методы для value classes
  @JsName("requestUserAttention") fun _requestUserAttention(requestType: JsInt?): Promise<JsAny>

  @JsName("theme") fun _theme(): Promise<JsString?>

  fun setResizable(resizable: JsBoolean): Promise<JsAny?>

  fun setTitle(title: JsString): Promise<JsAny?>

  fun maximize(): Promise<JsAny?>

  fun unmaximize(): Promise<JsAny?>

  fun minimize(): Promise<JsAny?>

  fun unminimize(): Promise<JsAny?>

  fun show(): Promise<JsAny?>

  fun hide(): Promise<JsAny?>

  fun close(): Promise<JsAny?>

  fun destroy(): Promise<JsAny?>

  fun setDecorations(decorations: JsBoolean): Promise<JsAny?>

  fun setAlwaysOnTop(alwaysOnTop: JsBoolean): Promise<JsAny?>

  fun setSize(size: JsAny): Promise<JsAny?>

  fun setPosition(position: JsAny): Promise<JsAny?>

  fun setFullscreen(fullscreen: JsBoolean): Promise<JsAny?>

  fun setFocus(): Promise<JsAny?>

  fun setIcon(icon: JsAny): Promise<JsAny?>

  fun startDragging(): Promise<JsAny?>
}

