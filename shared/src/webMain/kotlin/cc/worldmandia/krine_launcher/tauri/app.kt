@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/app")

package cc.worldmandia.krine_launcher.tauri

import js.core.JsPrimitives.toKotlinString
import js.promise.Promise
import js.promise.await

external fun getVersion(): Promise<JsString>

external fun getName(): Promise<JsString>

external fun getTauriVersion(): Promise<JsString>

external fun show(): Promise<JsAny?>

external fun hide(): Promise<JsAny?>
