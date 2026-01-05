@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/path")

package cc.worldmandia.krine_launcher.tauri

import js.promise.Promise

external fun appConfigDir(): Promise<JsString?>

external fun appDataDir(): Promise<JsString?>

external fun resourceDir(): Promise<JsString?>

external fun join(vararg paths: JsString): Promise<JsString?>

external fun resolve(vararg paths: JsString): Promise<JsString?>

external fun normalize(path: JsString): Promise<JsString?>
