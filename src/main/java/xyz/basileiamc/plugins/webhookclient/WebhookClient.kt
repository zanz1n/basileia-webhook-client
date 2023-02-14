package xyz.basileiamc.plugins.webhookclient

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import xyz.basileiamc.plugins.webhookclient.jobs.PostJob
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.PluginConfig
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.readBannedUsers

class WebhookClient : JavaPlugin() {
    override fun onEnable() {
        this.saveDefaultConfig()

        PluginConfig.update(this.config)
        logger.info(Json.encodeToString(readBannedUsers()))

        PostJob().runTaskTimerAsynchronously(this, 60, PluginConfig.postDelay)
//        GetJob().runTaskTimerAsynchronously(this, 60, PluginConfig.getDelay)
    }

    override fun onDisable() {
        Bukkit.getServer().scheduler.cancelTasks(this)
    }
}