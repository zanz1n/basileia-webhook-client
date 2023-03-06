package xyz.basileiamc.plugins.webhookclient.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.Bukkit
import xyz.basileiamc.plugins.webhookclient.serializable.LinkedMinecraftUser
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.readUserCache
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

private val logger = Bukkit.getLogger()

fun readLinkedAccounts(): List<LinkedMinecraftUser> {
    val userCache = readUserCache()

    var linkedAccountsString = ""
    val br = BufferedReader(FileReader(File("plugins/DiscordSRV/","linkedaccounts.json")))
    var line: String?
    while (br.readLine().also { line = it } != null) {
        linkedAccountsString += line
    }
    val element = Json.parseToJsonElement(linkedAccountsString).jsonObject
    br.close()
    val linkedAccounts = mutableListOf<LinkedMinecraftUser>()

    var unresolved = 0

    element.keys.forEach { dcId ->
        run {
            val playerUniqueID = element.get(dcId)!!.jsonPrimitive.content
            val playerName = userCache.find { el -> el.uuid == playerUniqueID }?.name
            if (playerName == null) {
                unresolved++
                return@run
            }
            linkedAccounts.add(
                LinkedMinecraftUser(
                uuid = playerUniqueID,
                discordId = dcId,
                nameLowerCase = playerName.lowercase(),
                nameWithCaps = playerName
            )
            )
        }
    }

    logger.warning("$unresolved linked accounts cannot be resolved")

    return linkedAccounts
}