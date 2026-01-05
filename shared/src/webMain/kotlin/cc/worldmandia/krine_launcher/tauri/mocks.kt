@file:OptIn(ExperimentalWasmJsInterop::class)
@file:JsModule("@tauri-apps/api/mocks")

package cc.worldmandia.krine_launcher.tauri

import js.objects.JsPlainObject

@JsPlainObject
external interface MockIPCOptions {
  val shouldMockEvents: JsBoolean?
}

external fun mockIPC(
    cb: (cmd: JsString, payload: JsAny?) -> JsAny?,
    options: MockIPCOptions? = definedExternally,
)

external fun mockWindows(current: JsString, vararg additionalWindows: JsString)

external fun clearMocks()
