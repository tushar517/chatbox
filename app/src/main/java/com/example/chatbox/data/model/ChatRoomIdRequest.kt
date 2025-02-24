package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomIdRequest(
    val senderId: String,
    val receiverId: String
)
