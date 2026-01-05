@file:OptIn(ExperimentalWasmJsInterop::class)

package cc.worldmandia.krine_launcher.tauri

import js.core.JsInt
import js.core.JsPrimitives.toJsInt

value class Theme(val value: JsString) {
  companion object {
    val Light = Theme("light".toJsString())
    val Dark = Theme("dark".toJsString())
  }
}

value class TitleBarStyle(val value: JsString) {
  companion object {
    val Visible = TitleBarStyle("visible".toJsString())
    val Transparent = TitleBarStyle("transparent".toJsString())
    val Overlay = TitleBarStyle("overlay".toJsString())
  }
}

value class UserAttentionType(val value: JsInt) {
  companion object {
    val Critical = UserAttentionType(1.toJsInt())
    val Informational = UserAttentionType(2.toJsInt())
  }
}
