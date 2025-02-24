package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id:Long,
    val chatRoomId:String,
    val content:String,
    val contentType:String,
    val senderId:String,
    val recipientId:String,
    val timeStamp:String

)
