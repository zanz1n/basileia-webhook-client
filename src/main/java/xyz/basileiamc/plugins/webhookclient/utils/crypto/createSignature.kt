package xyz.basileiamc.plugins.webhookclient.utils.crypto

import jakarta.xml.bind.DatatypeConverter
import org.bukkit.Bukkit
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.PluginConfig
import java.security.MessageDigest

fun createSignature(body: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    Bukkit.getLogger().info(PluginConfig.secret.toByteArray().toString())
    messageDigest.update(PluginConfig.secret.toByteArray())
    messageDigest.update(body.toByteArray())
    return DatatypeConverter.printHexBinary(messageDigest.digest()).lowercase()
}

fun createSignature(): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    messageDigest.update(PluginConfig.secret.toByteArray())
    return DatatypeConverter.printHexBinary(messageDigest.digest()).lowercase()
}
