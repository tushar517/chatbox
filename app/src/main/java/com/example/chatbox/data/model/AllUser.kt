package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AllUser(
    val fullName: String,
    val gender: String,
    val lastSeen: String,
    val userName: String
)