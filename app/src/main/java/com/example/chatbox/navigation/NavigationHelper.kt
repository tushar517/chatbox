package com.example.chatbox.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data object Home

@Parcelize
@Serializable
data class UserDetail(val userName:String,val password:String):Parcelable

@Serializable
object Login

@Serializable
object Landing

@Serializable
object SignUp

@Serializable
object AuthRoute
