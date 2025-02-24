package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AllChatReponse(
    val chatMessages:List<ChatResponse>
)
