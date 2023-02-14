package xyz.basileiamc.plugins.webhookclient.serializable

import kotlinx.serialization.Serializable

@Serializable
data class LinkedMinecraftUser(
    val uuid: String,
    val discordId: String,
    val nameWithCaps: String,
    val nameLowerCase: String
)