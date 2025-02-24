package com.example.chatbox.ui.viewmodel.chat

sealed interface ChatEvent {
    data class GetChatRoomId(val senderId:String,val recipientId:String) : ChatEvent
    data class SendMessage(val message:String) : ChatEvent
    data class OnMessageChange(val message:String) : ChatEvent
}