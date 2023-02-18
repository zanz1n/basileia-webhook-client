package xyz.basileiamc.plugins.webhookclient.jobs

import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import xyz.basileiamc.plugins.webhookclient.utils.RequestExecutor
import java.net.http.HttpClient
import java.time.Duration

class GetJob : BukkitRunnable() {
    override fun run() {
        val executor = RequestExecutor(
            HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build())

        try {
            val response = executor.executeAndParseUnbansGet()
            Bukkit.getLogger().info(response.toString())
            response.data.forEach { element ->
                run {
                    Bukkit.getBanList(BanList.Type.NAME).pardon(element)
                }
            }
        } catch (err: Exception) { PostJob.errorResponse(err) }
    }
}