@file:OptIn(ExperimentalWasmJsInterop::class, ExperimentalSerializationApi::class)

package cc.worldmandia.krine_launcher.tauri

import js.buffer.ArrayBuffer
import js.buffer.toByteArray
import js.promise.Promise
import js.promise.await
import js.typedarrays.toUint8Array
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf

val protoBuf = ProtoBuf { encodeDefaults = true }

/** Core API for communicating with the Tauri backend. corresponds to "@tauri-apps/api/core" */
@JsModule("@tauri-apps/api/core")
external object CoreApi {

  /**
   * Sends a message to the backend.
   *
   * @param cmd The command name.
   * @param args The optional arguments (Object, Array, or Uint8Array).
   * @param options The request options.
   * @return A promise resolving to the backend response.
   */
  fun <T : JsAny> invoke(
      cmd: String,
      args: JsAny? = definedExternally,
      options: InvokeOptions? = definedExternally,
  ): Promise<T>

  /** Convert a device file path to an URL that can be loaded by the webview. */
  fun convertFileSrc(filePath: String, protocol: String? = definedExternally): String

  /**
   * Stores the callback in a known location, and returns an identifier that can be passed to the
   * backend.
   */
  fun <T : JsAny> transformCallback(
      callback: ((T) -> Unit)? = definedExternally,
      once: Boolean? = definedExternally,
  ): Int

  /** Checks if the app is currently running in a Tauri context. */
  fun isTauri(): Boolean

  // --- Plugin Management ---

  /** Adds a listener to a plugin event. */
  fun <T : JsAny> addPluginListener(
      plugin: String,
      event: String,
      cb: (T) -> Unit,
  ): Promise<PluginListener>

  /** Get permission state for a plugin. */
  fun <T : JsAny> checkPermissions(plugin: String): Promise<T>

  /** Request permissions for a plugin. */
  fun <T : JsAny> requestPermissions(plugin: String): Promise<T>

  /** A key to be used to implement a special function for IPC serialization. */
  val SERIALIZE_TO_IPC_FN: String
}

/**
 * Extension to handle Protobuf IPC automatically.
 *
 * Usage:
 * ```kotlin
 * val result: MyResponse = CoreApi.invokeProto("my_command", MyRequest(...))
 * ```
 */
suspend inline fun <reified Request, reified Response> CoreApi.invokeProto(
    cmd: String,
    request: Request,
): Response {
  val encodedBytes = protoBuf.encodeToByteArray(request)

  val jsBytes = encodedBytes.toUint8Array()

  val args = createPayloadArg(jsBytes)

  val responsePromise: Promise<ArrayBuffer> = this.invoke(cmd, args)

  val responseBuffer = responsePromise.await()
  return protoBuf.decodeFromByteArray(responseBuffer.toByteArray())
}

/** Helper to create the JS object `{ payload: <data> }` efficiently. */
@Suppress("UNUSED_PARAMETER")
fun createPayloadArg(payload: JsAny): JsAny = js("({ payload: payload })")

/** A channel for sending data from backend to frontend. */
@JsModule("@tauri-apps/api/core")
external class Channel<T : JsAny>(onmessage: ((T) -> Unit)? = definedExternally) : JsAny {
  /** The callback id returned from transformCallback */
  val id: Int

  /** The message handler callback */
  var onmessage: (T) -> Unit

  /** Serializes the channel to a string JSON representation */
  fun toJSON(): String
}

/** A rust-backed resource stored through `tauri::Manager::resources_table` API. */
@JsModule("@tauri-apps/api/core")
open external class Resource(rid: Int) : JsAny {
  /** The resource ID */
  val rid: Int

  /** Destroys and cleans up this resource from memory. */
  fun close(): Promise<JsAny?>
}

/** The listener object returned when adding a plugin listener. */
external class PluginListener : JsAny {
  val plugin: String
  val event: String
  val channelId: Int

  /** Unregisters the listener */
  fun unregister(): Promise<JsAny?>
}

/** Options for the invoke command. */
external interface InvokeOptions : JsAny {
  /** Request headers (HeadersInit) */
  var headers: JsAny?
}

/** Permission state values: 'granted', 'denied', 'prompt', 'prompt-with-rationale'. */
typealias PermissionState = String
