@file:OptIn(ExperimentalSerializationApi::class)

package cc.worldmandia.krine_launcher.proto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class MinecraftData(
    val platform: String,
    val version: String,
)

@Serializable
data class DownloadProgress(
    val file: String,
    val percent: Int,
)