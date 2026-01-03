@file:OptIn(ExperimentalWasmJsInterop::class)

package cc.worldmandia.krine_launcher.tauri

import js.promise.Promise

/**
 * Provides APIs to create webviews, communicate with other webviews and manipulate the current
 * webview.
 *
 * #### Webview events
 *
 * Events can be listened to using [Webview.listen]:
 * ```kotlin
 * val currentWebview = WebviewApi.getCurrentWebview()
 * currentWebview.listen<JsString>("my-webview-event") { event -> ... }
 * ```
 */
external class Webview(window: Window, label: String, options: WebviewOptions) : JsAny {
  /**
   * The webview label. It is a unique identifier for the webview, can be used to reference it
   * later.
   */
  val label: String

  /** The window hosting this webview. */
  val window: Window

  // Note: 'listeners' omitted as it requires complex Record binding not typically used directly in
  // Kotlin.

  companion object {
    /**
     * Gets the Webview for the webview associated with the given label.
     *
     * @param label The webview label.
     * @return The Webview instance to communicate with the webview or null if the webview doesn't
     *   exist.
     */
    fun getByLabel(label: String): Promise<Webview?>

    /** Get an instance of `Webview` for the current webview. */
    fun getCurrent(): Webview

    /** Gets a list of instances of `Webview` for all available webviews. */
    fun getAll(): Promise<JsArray<Webview>>
  }

  /**
   * Listen to an emitted event on this webview.
   *
   * @param event Event name. Must include only alphanumeric characters, `-`, `/`, `:` and `_`.
   * @param handler Event handler.
   * @return A promise resolving to a function (UnlistenFn) to unlisten to the event.
   */
  fun <T : JsAny> listen(event: String, handler: (Event<T>) -> Unit): Promise<JsAny>

  /**
   * Listen to an emitted event on this webview only once.
   *
   * @param event Event name. Must include only alphanumeric characters, `-`, `/`, `:` and `_`.
   * @param handler Event handler.
   * @return A promise resolving to a function (UnlistenFn) to unlisten to the event.
   */
  fun <T : JsAny> once(event: String, handler: (Event<T>) -> Unit): Promise<JsAny>

  /**
   * Emits an event to all targets.
   *
   * @param event Event name. Must include only alphanumeric characters, `-`, `/`, `:` and `_`.
   * @param payload Event payload.
   */
  fun <T : JsAny> emit(event: String, payload: T? = definedExternally): Promise<JsAny?>

  /**
   * Emits an event to all targets matching the given target.
   *
   * @param target Label of the target Window/Webview/WebviewWindow or raw EventTarget object.
   * @param event Event name. Must include only alphanumeric characters, `-`, `/`, `:` and `_`.
   * @param payload Event payload.
   */
  fun <T : JsAny> emitTo(
      target: JsAny,
      event: String,
      payload: T? = definedExternally,
  ): Promise<JsAny?>

  /**
   * The position of the top-left hand corner of the webview's client area relative to the top-left
   * hand corner of the desktop.
   *
   * @return The webview's position.
   */
  fun position(): Promise<PhysicalPosition>

  /**
   * The physical size of the webview's client area. The client area is the content of the webview,
   * excluding the title bar and borders.
   *
   * @return The webview's size.
   */
  fun size(): Promise<PhysicalSize>

  /**
   * Closes the webview.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun close(): Promise<JsAny?>

  /**
   * Resizes the webview.
   *
   * @param size The logical or physical size.
   * @return A promise indicating the success or failure of the operation.
   */
  fun setSize(size: JsAny): Promise<JsAny?>

  /**
   * Sets the webview position.
   *
   * @param position The new position, in logical or physical pixels.
   * @return A promise indicating the success or failure of the operation.
   */
  fun setPosition(position: JsAny): Promise<JsAny?>

  /**
   * Bring the webview to front and focus.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun setFocus(): Promise<JsAny?>

  /**
   * Sets whether the webview should automatically grow and shrink its size and position when the
   * parent window resizes.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun setAutoResize(autoResize: Boolean): Promise<JsAny?>

  /**
   * Hide the webview.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun hide(): Promise<JsAny?>

  /**
   * Show the webview.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun show(): Promise<JsAny?>

  /**
   * Set webview zoom level.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun setZoom(scaleFactor: Double): Promise<JsAny?>

  /**
   * Moves this webview to the given label.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun reparent(window: JsAny): Promise<JsAny?>

  /**
   * Clears all browsing data for this webview.
   *
   * @return A promise indicating the success or failure of the operation.
   */
  fun clearAllBrowsingData(): Promise<JsAny?>

  /**
   * Specify the webview background color.
   *
   * #### Platfrom-specific:
   * - **macOS / iOS**: Not implemented.
   * - **Windows**: Transparency/alpha behavior varies by version.
   *
   * @param color Array of [R, G, B, A] or null.
   * @return A promise indicating the success or failure of the operation.
   * @since 2.1.0
   */
  fun setBackgroundColor(color: JsArray<JsNumber>?): Promise<JsAny?>

  /**
   * Listen to a file drop event. The listener is triggered when the user hovers the selected files
   * on the webview, drops the files or cancels the operation.
   *
   * @return A promise resolving to a function to unlisten to the event.
   */
  fun onDragDropEvent(handler: (Event<DragDropEvent>) -> Unit): Promise<JsAny>
}

@JsModule("@tauri-apps/api/webview")
external object WebviewApi {
  /**
   * Get an instance of `Webview` for the current webview.
   *
   * @since 2.0.0
   */
  fun getCurrentWebview(): Webview

  /**
   * Gets a list of instances of `Webview` for all available webviews.
   *
   * @since 2.0.0
   */
  fun getAllWebviews(): Promise<JsArray<Webview>>
}

/**
 * Configuration for the webview to create.
 *
 * @since 2.0.0
 */
external interface WebviewOptions : JsAny {
  /** Remote URL or local file path to open. */
  var url: String?
  /** The initial vertical position. */
  var x: Double
  /** The initial horizontal position. */
  var y: Double
  /** The initial width. */
  var width: Double
  /** The initial height. */
  var height: Double
  /** Whether the webview is transparent or not. */
  var transparent: Boolean?
  /** Whether the webview should have focus or not. @since 2.1.0 */
  var focus: Boolean?
  /** Whether the drag and drop is enabled or not on the webview. */
  var dragDropEnabled: Boolean?
  /** Whether clicking an inactive webview also clicks through to the webview on macOS. */
  var acceptFirstMouse: Boolean?
  /** The user agent for the webview. */
  var userAgent: String?
  /** Whether or not the webview should be launched in incognito mode. */
  var incognito: Boolean?
  /** The proxy URL for the WebView for all network requests. */
  var proxyUrl: String?
  /** Whether page zooming by hotkeys is enabled. */
  var zoomHotkeysEnabled: Boolean?
  /** Sets whether the custom protocols should use `https://<scheme>.localhost`. @since 2.1.0 */
  var useHttpsScheme: Boolean?
  /** Whether web inspector is enabled or not. @since 2.1.0 */
  var devtools: Boolean?
  /** Set the window and webview background color. @since 2.1.0 */
  var backgroundColor: JsArray<JsNumber>?
  /** Whether we should disable JavaScript code execution on the webview or not. */
  var javascriptDisabled: Boolean?
  /** on macOS and iOS there is a link preview on long pressing links. */
  var allowLinkPreview: Boolean?
  /** Allows disabling the input accessory view on iOS. */
  var disableInputAccessoryView: Boolean?
  /** Set a custom path for the webview's data directory. @since 2.9.0 */
  var dataDirectory: String?
  /** Initialize the WebView with a custom data store identifier. @since 2.9.0 */
  var dataStoreIdentifier: JsArray<JsNumber>?
  /** Specifies the native scrollbar style to use with the webview. */
  var scrollBarStyle: String?
}

/** The drag and drop event types. */
external interface DragDropEvent : JsAny {
  /** 'enter', 'over', 'drop', or 'leave' */
  val type: String
  /** Paths involved in the drop (only for 'enter' and 'drop') */
  val paths: JsArray<JsString>?
  /** Position of the drop (only for 'enter', 'over', 'drop') */
  val position: PhysicalPosition?
}

// --- Supporting Types ---

external interface PhysicalPosition : JsAny {
  val x: Int
  val y: Int
}

external interface PhysicalSize : JsAny {
  val width: Int
  val height: Int
}

/** Placeholder for Window class */
external class Window : JsAny
