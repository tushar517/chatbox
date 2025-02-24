package com.example.chatbox.navigation

import android.os.Parcelable
import com.example.chatbox.data.model.AllUser
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data object Home

@Parcelize
@Serializable
data class UserDetail(val userName:String,val password:String):Parcelable

@Serializable
data object Login

@Serializable
data object Landing

@Serializable
data object SignUp

@Serializable
data object AuthRoute

@Serializable
data object User

@Serializable
data class Chat(val fullName: String,
                val gender: String,
                val lastSeen: String,
                val userName: String,
                val profileImg:String)

@Serializable
data object Profile

@Serializable
data object Setting

@Serializable
data object EditDetail