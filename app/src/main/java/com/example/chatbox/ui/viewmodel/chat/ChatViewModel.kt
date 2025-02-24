package com.example.chatbox.ui.viewmodel.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.AllChatReponse
import com.example.chatbox.data.model.AllUser
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.ChatResponse
import com.example.chatbox.data.model.ChatRoomIdRequest
import com.example.chatbox.data.network.Repository
import com.example.chatbox.navigation.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val dataStoreHelper: DataStoreHelper,
    val repository: Repository,
    val stompClient: StompClient,
    val stateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> get() = _state

    init {
        initState()
    }


    private fun initState() {
        try {
            val receiverDetail = AllUser(
                userName = stateHandle.toRoute<Chat>().userName,
                gender = stateHandle.toRoute<Chat>().gender,
                fullName = stateHandle.toRoute<Chat>().fullName,
                lastSeen = stateHandle.toRoute<Chat>().lastSeen,
                profileImg = stateHandle.toRoute<Chat>().profileImg
            )
            _state.value = _state.value.copy(
                isLoading = true
            )
            _state.value = _state.value.copy(
                receiverDetail = receiverDetail,
                senderUserName = getUserName()
            )
            Log.e("receiverDetail", "$receiverDetail")
            getChatRoomId()
        } catch (ex: Exception) {
            Log.e("error", ex.localizedMessage ?: "")
        }
    }

    private fun getChatRoomId() {
        viewModelScope.launch {
            state.value.let { value ->
                try {
                    val response = repository.getChatRoomId(
                        ChatRoomIdRequest(
                            value.senderUserName,
                            value.receiverDetail.userName
                        )
                    )
                    var allChatResponse: ApiResult<AllChatReponse> = ApiResult.Empty()

                    var chatRoomId = ""
                    var chatList = listOf<ChatResponse>()
                    if (response is ApiResult.Success) {
                        chatRoomId = response.data.response.chatRoomId
                        allChatResponse = repository.getAllChat(chatRoomId)
                        if (allChatResponse is ApiResult.Success) {
                            chatList = allChatResponse.data.response.chatMessages
                        }
                    }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        chatRoomId = chatRoomId,
                        chatRoomApi = response,
                        allChats = chatList,
                        chatApi = allChatResponse
                    )
                    stompConnect(chatRoomId)
                } catch (ex: Exception) {
                    Log.e("error", ex.localizedMessage ?: "")
                }
            }
        }
    }

    private fun getUserName(): String {

        return runBlocking {
            dataStoreHelper.getData(
                PreferenceKey.userName,
                ""
            ).first()
        }
    }

    private fun stompConnect(chatRoomId: String) {
        if (!stompClient.isConnected) {
            stompClient.connect()
        }
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            stompClient
                .topic("/topic/messages/$chatRoomId")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    val chatList = state.value.allChats.toMutableList()
                    val newChat = Json.decodeFromString<ChatResponse>(it.payload)
                    if(!state.value.allChats.contains(newChat)) {
                        chatList.add(newChat)
                        _state.value = _state.value.copy(
                            allChats = chatList
                        )
                    }
                }, {
                    Log.e("ChatSocketError", it.localizedMessage ?: "")
                })
        )
    }

    fun onEvent(chatEvent: ChatEvent) {
        when (chatEvent) {
            is ChatEvent.SendMessage -> {
                if(chatEvent.message.isNotBlank()) {
                    val compositeDisposable = CompositeDisposable()
                    val send = stompClient.send(
                        "/topic/chat", Json.encodeToString(
                            mapOf(
                                "chatRoomId" to state.value.chatRoomId,
                                "senderId" to state.value.senderUserName,
                                "recipientId" to state.value.receiverDetail.userName,
                                "content" to chatEvent.message
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

            is ChatEvent.OnMessageChange -> {
                _state.value = _state.value.copy(
                    message = chatEvent.message
                )
            }

            else -> {

            }
        }
    }
}