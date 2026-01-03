@file:OptIn(ExperimentalWasmJsInterop::class, ExperimentalSerializationApi::class)

package cc.worldmandia.krine_launcher.tauri

import js.buffer.ArrayBuffer
import js.buffer.toByteArray
import js.promise.Promise
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray

/**
 * The structure of the event object received in handlers. Note: This replaces the `TauriEvent`
 * interface from previous steps if you are using the specific event module.
 */
external interface Event<T : JsAny> : JsAny {
  /** Event name */
  val event: String
  /** Event identifier used to unlisten */
  val id: Int
  /** Event payload */
  val payload: T
}

/** Event listening options. */
external interface Options : JsAny {
  /** The event target to listen to. Accepts a String (label) or an [EventTarget] object. */
  var target: JsAny?
}

/**
 * Represents the target of an event. This corresponds to the discriminated union in TypeScript.
 *
 * Usage:
 * ```kotlin
 * val target = object : JsAny {
 * val kind = "Window"
 * val label = "main"
 * }.unsafeCast<EventTarget>()
 * ```
 */
external interface EventTarget : JsAny {
  /** 'Any', 'AnyLabel', 'App', 'Window', 'Webview', 'WebviewWindow' */
  var kind: String
  /** Required for 'AnyLabel', 'Window', 'Webview', 'WebviewWindow' */
  var label: String?
}

/** Bindings for the global event module. */
@JsModule("@tauri-apps/api/event")
external object EventApi {

  /**
   * Listen to an emitted event to any target.
   *
   * @param event Event name.
   * @param handler Event handler callback.
   * @param options Event listening options.
   * @return A promise resolving to a function (UnlistenFn) to unlisten.
   */
  fun <T : JsAny> listen(
      event: String,
      handler: (Event<T>) -> Unit,
      options: Options? = definedExternally,
  ): Promise<JsAny>

  /**
   * Listens once to an emitted event to any target.
   *
   * @param event Event name.
   * @param handler Event handler callback.
   * @param options Event listening options.
   * @return A promise resolving to a function (UnlistenFn) to unlisten.
   */
  fun <T : JsAny> once(
      event: String,
      handler: (Event<T>) -> Unit,
      options: Options? = definedExternally,
  ): Promise<JsAny>

  /**
   * Emits an event to all targets.
   *
   * @param event Event name.
   * @param payload Event payload.
   */
  fun <T : JsAny> emit(event: String, payload: T? = definedExternally): Promise<JsAny?>

  /**
   * Emits an event to all targets matching the given target.
   *
   * @param target Label string or [EventTarget] object.
   * @param event Event name.
   * @param payload Event payload.
   */
  fun <T : JsAny> emitTo(
      target: JsAny,
      event: String,
      payload: T? = definedExternally,
  ): Promise<JsAny?>

  // --- Enum Constants ---
  // Maps to the exported 'TauriEvent' enum object in JS
  val TauriEvent: TauriEventConstants
}

/**
 * Creates a Kotlin Flow for a specific Tauri event.
 *
 * Usage:
 * ```
 * EventApi.listenFlow("my-event").collect { event ->
 * println("Got payload: ${event.payload}")
 * }
 * ```
 */
inline fun <reified T> EventApi.listenFlow(event: String, options: Options? = null): Flow<T> =
    callbackFlow {
      // WebviewApi.getCurrentWebview().listen<ArrayBuffer>(event) { eventObj ->
      //    println(eventObj.payload)
      //    println(eventObj.event)
      //
      // trySend(protoBuf.decodeFromByteArray<T>(eventObj.payload.unsafeCast<ArrayBuffer>().toByteArray()))
      // }
      listen<ArrayBuffer>(
          event,
          { eventObj ->
            println(eventObj.payload)
            println(eventObj.event)
            trySend(protoBuf.decodeFromByteArray<T>(eventObj.payload.toByteArray()))
          },
          options,
      )

      awaitClose {}
    }

/** Enum constants for Tauri system events. Accessed via `EventApi.TauriEvent.WINDOW_RESIZED`. */
external interface TauriEventConstants : JsAny {
  val WINDOW_RESIZED: String
  val WINDOW_MOVED: String
  val WINDOW_CLOSE_REQUESTED: String
  val WINDOW_DESTROYED: String
  val WINDOW_FOCUS: String
  val WINDOW_BLUR: String
  val WINDOW_SCALE_FACTOR_CHANGED: String
  val WINDOW_THEME_CHANGED: String
  val WINDOW_CREATED: String
  val WEBVIEW_CREATED: String
  val DRAG_ENTER: String
  val DRAG_OVER: String
  val DRAG_DROP: String
  val DRAG_LEAVE: String
}
