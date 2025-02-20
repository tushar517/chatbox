package com.example.chatbox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AllUserResponse(
    val allUsers: List<AllUser>
)