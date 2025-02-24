package com.example.chatbox.ui.viewmodel.chat

import com.example.chatbox.data.model.AllChatReponse
import com.example.chatbox.data.model.AllUser
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.ChatResponse
import com.example.chatbox.data.model.ChatRoomResponse

data class ChatState(
    val isLoading:Boolean = false,
    val chatRoomId:String = "",
    val receiverDetail:AllUser = AllUser("","","","",""),
    val senderUserName:String = "",
    val chatRoomApi:ApiResult<ChatRoomResponse> = ApiResult.Empty(),
    val allChats:List<ChatResponse> = listOf(),
    val chatApi:ApiResult<AllChatReponse> = ApiResult.Empty(),
    val message:String = ""
)
