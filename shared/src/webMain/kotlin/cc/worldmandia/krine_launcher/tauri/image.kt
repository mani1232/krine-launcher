@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/image")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsInt
import js.promise.Promise
import js.promise.await

external interface ImageSize : JsAny {
  val width: JsInt
  val height: JsInt
}

external class Image(rid: JsInt) : Resource, JsAny {
  companion object {
    fun new(rgba: JsAny, width: JsInt, height: JsInt): Promise<Image>

    fun fromBytes(bytes: JsAny): Promise<Image>

    fun fromPath(path: JsString): Promise<Image>
  }

  fun rgba(): Promise<JsAny> // Uint8Array

  fun size(): Promise<ImageSize>
}
