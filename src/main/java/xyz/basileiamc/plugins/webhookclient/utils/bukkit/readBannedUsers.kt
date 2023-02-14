package xyz.basileiamc.plugins.webhookclient.utils.bukkit

import org.bukkit.BanList
import org.bukkit.Bukkit
import xyz.basileiamc.plugins.webhookclient.serializable.MinecraftBannedUser

fun readBannedUsers(): List<MinecraftBannedUser> {
    val bannedUsers = arrayListOf<MinecraftBannedUser>()
    val banList = Bukkit.getBanList(BanList.Type.NAME)
    Bukkit.getBannedPlayers().forEach { bannedPlayer ->
        run {
            if (bannedPlayer.name == null) {
                Bukkit.getLogger().info("bannedPlayer.name == null")
                return@run
            }
            val banEntry = banList.getBanEntry(bannedPlayer.name!!) ?: return@run

            var expiration = 0L

            if (banEntry.expiration != null) expiration = banEntry.expiration!!.time

            bannedUsers.add(MinecraftBannedUser(bannedPlayer.name!!, expiration, banEntry.reason))
        }
    }
    return bannedUsers
}