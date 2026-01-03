@file:OptIn(ExperimentalSerializationApi::class, ExperimentalWasmJsInterop::class)

package cc.worldmandia.krine_launcher.tauri

import cc.worldmandia.krine_launcher.proto.MinecraftData
import kotlinx.serialization.ExperimentalSerializationApi

suspend fun test(): String {
  val requestData = MinecraftData("Windows", "1.21.1")

  val responseData =
      CoreApi.invokeProto<MinecraftData, MinecraftData>("process_user_data", requestData)

  return responseData.toString()
}
