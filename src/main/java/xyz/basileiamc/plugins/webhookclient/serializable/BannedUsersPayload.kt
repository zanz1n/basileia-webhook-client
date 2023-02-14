package xyz.basileiamc.plugins.webhookclient.serializable

import kotlinx.serialization.Serializable

@Serializable
data class BannedUsersPayload(
    val data: List<MinecraftBannedUser>
)