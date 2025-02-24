package com.example.chatbox.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserModel(
    val userName: String = "Tushar",
    val fullName: String = "Tushar rawat",
    val gender:String="Male",
    val lastSeen: String = "2025-02-10 23:15:15.291000",
    val profileImg:String = ""
) : Parcelable