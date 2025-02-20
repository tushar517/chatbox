package com.example.chatbox.ui.screens

import android.R.id
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.chatbox.ui.components.CustomButton
import com.example.chatbox.ui.viewmodel.home.HomeEvent
import com.example.chatbox.ui.viewmodel.home.HomeState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader


@Composable
fun HomeScreen(
    mainNavHostController: NavHostController,
    onEvent: (HomeEvent) -> Unit,
    state: HomeState
) {

    onEvent(HomeEvent.getAuthtoken)
    LaunchedEffect(state.authToken) {
        if (state.authToken.isNotEmpty()) {
            val compositeDisposable = CompositeDisposable()
            val stomp = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                "ws://192.168.1.2:8088/ws/websocket",
                mapOf(
                    "Authorization" to "Bearer ${state.authToken}"
                )
            )
            stomp.connect(
                listOf(
                    StompHeader("Sec-WebSocket-Version", "13"),
                    StompHeader("Sec-WebSocket-Key", "uIcWxjqfLo45jA1p2WWKrw=="),
                    StompHeader("Connection", "Upgrade"),
                    StompHeader("Upgrade", "websocket"),
                    StompHeader("Authorization", "Bearer ${state.authToken}")
                )
            )
            val topic = stomp.topic("/topic/messages/1")

            compositeDisposable.add(stomp.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { event ->
                    when (event.type) {

                        LifecycleEvent.Type.OPENED -> {
                            Log.e("SocketError", "opened")
                        }

                        LifecycleEvent.Type.CLOSED -> {
                            Log.e("SocketError", "closed")
                        }

                        LifecycleEvent.Type.ERROR -> {
                            Log.e("SocketError", "${event.exception}")
                        }

                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                            Log.e("SocketError", "failed server heartbeat")
                        }

                        null -> {}
                    }

                })
            val dis = topic.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe({
                    Log.e("SocketMessage1", it.payload ?: "")
                }, { Log.e("SocketError", it.localizedMessage ?: "") })
            compositeDisposable.add(dis)

            onEvent(HomeEvent.callAllUserApi)
            val send = stomp.send(
                "/topic/chat", Json.encodeToString(
                    mapOf(
                        "chatId" to "1",
                        "senderId" to "tushar517",
                        "recipientId" to null,
                        "content" to "afdfaffadf"
                    )
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({

                }, { Log.e("SendSocketError", it.localizedMessage ?: "") })
            compositeDisposable.add(send)
        }

    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Center
        ) {
            CustomButton(
                "Send",
                {
                    onEvent(HomeEvent.SendMessage)
                },
                true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}