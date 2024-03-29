package xyz.basileiamc.plugins.webhookclient.utils.bukkit

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.basileiamc.plugins.webhookclient.serializable.MinecraftCachedUser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun readUserCache(): List<MinecraftCachedUser> {
    val br = BufferedReader(FileReader(File("usercache.json")))
    val userCacheString = br.readLine()
    br.close()
    return Json.decodeFromString(userCacheString)
}