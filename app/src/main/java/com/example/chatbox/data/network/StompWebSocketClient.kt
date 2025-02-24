package com.example.chatbox.data.network

import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader

class StompWebSocketClient(private val dataStoreHelper: DataStoreHelper) {
    fun stompClient(): StompClient {
        val stomp = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            "ws://192.168.1.6:8088/ws/websocket",
            mapOf(
                "Authorization" to "Bearer ${getAuthToken()}"
            )
        )
        stomp.connect(
            listOf(
                StompHeader("Sec-WebSocket-Version", "13"),
                StompHeader("Sec-WebSocket-Key", "uIcWxjqfLo45jA1p2WWKrw=="),
                StompHeader("Connection", "Upgrade"),
                StompHeader("Upgrade", "websocket"),
                StompHeader("Authorization", "Bearer ${getAuthToken()}")
            )
        )
        return stomp
    }
    private fun getAuthToken() =
        runBlocking {
            dataStoreHelper.getData(PreferenceKey.authToken, "").first().toString()
        }
}