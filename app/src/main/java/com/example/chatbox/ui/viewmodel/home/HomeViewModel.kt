package com.example.chatbox.ui.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.UserModel
import com.example.chatbox.data.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreHelper: DataStoreHelper,
    private val stomp: StompClient
):ViewModel() {
    private val _stateFlow = MutableStateFlow(HomeState())
    val stateFlow get() = _stateFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),HomeState())

    fun onEvent(event:HomeEvent){
        when(event){
            is HomeEvent.callAllUserApi -> {
                viewModelScope.launch {
                    repository.getAllUsers()
                }
            }
            is HomeEvent.SendMessage->{
                val compositeDisposable = CompositeDisposable()
                val send =stomp.send("/user/connectedUser", Json.encodeToString(UserModel()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({

                },{ Log.e("SendSocketError",it.localizedMessage?:"")})
                compositeDisposable.add(send)
            }
            is HomeEvent.SubscribeToSocket->{
                val compositeDisposable = CompositeDisposable()

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
            else -> {}
        }
    }
}