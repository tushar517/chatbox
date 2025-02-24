package com.example.chatbox.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AllUser(
    val fullName: String,
    val gender: String,
    val lastSeen: String,
    val userName: String,
    val profileImg:String
):Parcelable