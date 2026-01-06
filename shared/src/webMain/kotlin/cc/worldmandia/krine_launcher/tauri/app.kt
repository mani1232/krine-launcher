@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/app")

package cc.worldmandia.krine_launcher.tauri

import js.promise.Promise

external fun getVersion(): Promise<JsString>

external fun getName(): Promise<JsString>

external fun getTauriVersion(): Promise<JsString>

external fun show(): Promise<JsAny?>

external fun hide(): Promise<JsAny?>
