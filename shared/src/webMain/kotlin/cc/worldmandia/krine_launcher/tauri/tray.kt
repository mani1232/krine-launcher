@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/tray")

package cc.worldmandia.krine_launcher.tauri

import js.buffer.ArrayBuffer
import js.objects.JsPlainObject
import js.promise.Promise

@JsPlainObject
external interface TrayIconOptions {
  val id: JsString?
  val tooltip: JsString?
  val title: JsString?
  val icon: ArrayBuffer?
  val menu: JsAny? // TODO Menu | Submenu
  val iconAsTemplate: JsBoolean?
  val showMenuOnLeftClick: JsBoolean?
  // TODO action?: (event: TrayIconEvent) => void;
}

external class TrayIcon : Resource, JsAny {
  val id: JsString

  companion object {
    fun getById(id: String): Promise<TrayIcon?>

    fun new(options: TrayIconOptions? = definedExternally): Promise<TrayIcon>
  }

  fun setIcon(icon: ArrayBuffer?): Promise<JsAny?>

  // TODO Menu | Submenu | null
  fun setMenu(menu: JsAny?): Promise<JsAny?>

  fun setTooltip(tooltip: JsString?): Promise<JsAny?>

  fun setTitle(title: JsString?): Promise<JsAny?>

  fun setVisible(visible: JsBoolean): Promise<JsAny?>
}
