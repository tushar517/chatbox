package com.example.chatbox.data.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.hildan.krossbow.websocket.WebSocketFrame
import org.hildan.krossbow.websocket.ktor.KtorWebSocketClient
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage

class KtorWebSocketClient(private val authToken: String) {
    val webSocketClient: HttpClient =
        HttpClient {
            install(WebSockets)

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
                headers
                    .append("Authorization", "Bearer $authToken")
            }
            engine {
                headers {
                    append("Upgrade", "websocket")
                    append("Connection", "Upgrade")
                }
            }
        }

    val stopClient = KtorWebSocketClient(httpClient = webSocketClient)

    fun connect(url: String, onMessageReceived: (StompMessage) -> Unit) {
        GlobalScope.launch {
            val stomp = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                "ws://10.0.2.2:8088/ws"
            )
            stomp.connect(
                listOf(
                    StompHeader("Sec-WebSocket-Version", "13"),
                    StompHeader("Sec-WebSocket-Key", "uIcWxjqfLo45jA1p2WWKrw=="),
                    StompHeader("Connection", "Upgrade"),
                    StompHeader("Upgrade", "websocket")
                )
            )
                val topic = stomp.topic(url)
                topic.
                    subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe(onMessageReceived,{Log.e("socketError",it.localizedMessage?:"")})

                stomp.send("/user/disconnectUser","My first message").subscribe()




            try {
                /*val st = stompClient.connect(
                    url,
                    headers = mapOf(
                        "Sec-WebSocket-Version" to "13",
                        "Sec-WebSocket-Key" to "uIcWxjqfLo45jA1p2WWKrw==",
                        ),

                )
                st.incomingFrames.collect{
                    onMessageReceived(it)
                }*/
                /*webSocketClient.wss(url) {

                    for (message in incoming) {
                        onMessageReceived(message)
//                        Log.d("socket", message.toString())
                    }
                }*/
            } catch (ex: Exception) {
                Log.e("WebSocketError", ex.localizedMessage ?: "")
            }
        }
    }

    /*stompClient.subscribe(
    destination: "/user/topic",
    callback: (StompFrame frame) {
        try {
            print("stomp user ${frame.body!}");
            UserModel user = UserModel.fromJson(json.decode(frame.body!));
            if (user.status) {
                bloc.add(StompAddUser(user: user));
            } else {
                bloc.add(StompRemoveUser(user: user));
            }
        } catch (ex) {
            print(ex);
        }
    },
    );*/
    fun closeClient() {

        webSocketClient.close()
    }
}
