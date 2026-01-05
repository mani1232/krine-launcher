@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/dpi")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsDouble
import js.core.JsInt
import js.objects.JsPlainObject

@JsPlainObject
external interface LogicalSize : JsAny {
  val type: JsString
  val width: JsInt
  val height: JsInt
}

@JsPlainObject
external interface PhysicalSize : JsAny {
  val type: JsString
  val width: JsInt
  val height: JsInt
}

@JsPlainObject
external interface LogicalPosition : JsAny {
  val type: JsString
  val x: JsDouble
  val y: JsDouble
}

@JsPlainObject
external interface PhysicalPosition : JsAny {
  val type: JsString
  val x: JsInt
  val y: JsInt
}
