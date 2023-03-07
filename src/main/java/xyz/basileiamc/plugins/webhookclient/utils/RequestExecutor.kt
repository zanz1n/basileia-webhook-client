package xyz.basileiamc.plugins.webhookclient.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import xyz.basileiamc.plugins.webhookclient.serializable.BannedUsersPayload
import xyz.basileiamc.plugins.webhookclient.serializable.LinkedAccountsPayload
import xyz.basileiamc.plugins.webhookclient.serializable.UnbansPayload
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.PluginConfig
import xyz.basileiamc.plugins.webhookclient.utils.bukkit.readBannedUsers
import xyz.basileiamc.plugins.webhookclient.utils.crypto.createSignature
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class RequestExecutor(private val httpClient: HttpClient) {
    private fun requestBuilder(route: String): HttpRequest.Builder {
        return HttpRequest.newBuilder()
            .uri(URI.create(PluginConfig.webhookUri + route))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .timeout(Duration.ofSeconds(5))
    }

    fun executeLinkedAccountsPost(): HttpResponse<String> {
        val linkedAccounts = Json.encodeToString(LinkedAccountsPayload(readLinkedAccounts()))

        val signature = createSignature(linkedAccounts)

        val request = requestBuilder("/api/restricted/minecraft/webhook/discord/accounts")
            .POST(HttpRequest.BodyPublishers.ofString(linkedAccounts))
            .header("x-store-sig", signature)
            .build()

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun executeBansPost(): HttpResponse<String> {
        val bannedUsers = Json.encodeToString(BannedUsersPayload(readBannedUsers()))

        val signature = createSignature(bannedUsers)

        val request = requestBuilder("/api/restricted/minecraft/webhook/bans")
            .POST(HttpRequest.BodyPublishers.ofString(bannedUsers))
            .header("x-store-sig", signature)
            .build()

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun executeAndParseUnbansGet(): UnbansPayload {
        val response = executeUnbansGet()
        Bukkit.getLogger().info("Executed get job ${response.uri()} - STATUS: ${response.statusCode()}")
        return Json.decodeFromString(response.body())
    }

    fun executeUnbansGet(): HttpResponse<String> {
        val signature = createSignature()

        val request = requestBuilder("/unbans")
            .GET()
            .header("x-store-sig", signature)
            .build()

        return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

}