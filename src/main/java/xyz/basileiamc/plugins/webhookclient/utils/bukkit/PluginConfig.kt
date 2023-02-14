package xyz.basileiamc.plugins.webhookclient.utils.bukkit

import org.bukkit.configuration.file.FileConfiguration

object PluginConfig {
    lateinit var secret: String
    lateinit var hashAlgorithm: String
    var getDelay: Long = 200
    var postDelay: Long = 200
    lateinit var webhookUri: String

    fun update(config: FileConfiguration) {
        secret = config.getString("authorization.webhookSecret")!!
        getDelay = config.getLong("webhook.fetchDelay")
        postDelay = config.getLong("webhook.postDelay")
        hashAlgorithm = config.getString("authorization.hash")!!
        webhookUri = config.getString("webhook.uri")!!
    }
}