package xyz.basileiamc.plugins.webhookclient.jobs

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import xyz.basileiamc.plugins.webhookclient.utils.RequestExecutor
import java.lang.Exception
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.time.Duration

class PostJob : BukkitRunnable() {
    private fun logResponse(response: HttpResponse<String>) {
        Bukkit.getLogger().info("Executed post job ${response.uri()} - STATUS: ${response.statusCode()}")
    }

    private fun errorResponse(err: Exception) {
        Bukkit.getLogger().warning("Post job failed: ${err.message}")
    }

    override fun run() {
        val executor = RequestExecutor(
            HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build())

        try {
            val response = executor.executeLinkedAccountsPost()
            logResponse(response)
        } catch (err: Exception) { errorResponse(err) }

        try {
            val response = executor.executeBansPost()
            logResponse(response)
        } catch (err: Exception) { errorResponse(err) }
    }
}