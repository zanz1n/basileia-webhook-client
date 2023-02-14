package xyz.basileiamc.plugins.webhookclient.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftBannedUser(
    val username: String,
    val until: Long,
    val reason: String?
)