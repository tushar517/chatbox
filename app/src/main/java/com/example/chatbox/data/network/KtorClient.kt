package com.example.chatbox.data.network

import android.util.Log
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class KtorClient(private val dataStoreHelper: DataStoreHelper) {
    val httpClient: HttpClient =
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("api call", message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
//                url.host = "10.0.2.2:8088"
                url.host = "192.168.1.2:8088"
                url.protocol = URLProtocol.HTTP
                headers
                    .append("Authorization", "Bearer ${getAuthToken()}")
                headers.append("Content-Type", "application/json")
            }
        }

    private fun getAuthToken() =
        runBlocking {
            dataStoreHelper.getData(PreferenceKey.authToken, "").first().toString()
        }
}

