package xyz.basileiamc.plugins.webhookclient.serializable

import kotlinx.serialization.Serializable

@Serializable
data class MinecraftCachedUser (
    val name: String,
    val uuid: String,
    val expiresOn: String
)