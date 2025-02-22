package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val fullName: String,
    val gender: String,
    val password: String,
    val profileImg: String,
    val userName: String
)