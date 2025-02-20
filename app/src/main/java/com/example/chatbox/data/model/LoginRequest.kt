package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val password: String,
    val username: String
)