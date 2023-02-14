package xyz.basileiamc.plugins.webhookclient.serializable

import kotlinx.serialization.Serializable

@Serializable
data class LinkedAccountsPayload(
    val data: List<LinkedMinecraftUser>
)