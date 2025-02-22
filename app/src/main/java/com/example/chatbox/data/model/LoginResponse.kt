package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token:String,
    val userDetail:AllUser
)