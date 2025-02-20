package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CommonResponse<T>(
    val status:Boolean,
    val description: String,
    val response:T,
)
