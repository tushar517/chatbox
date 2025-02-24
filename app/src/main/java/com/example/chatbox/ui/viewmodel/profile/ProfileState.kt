package com.example.chatbox.ui.viewmodel.profile

import com.example.chatbox.data.model.AllUser

data class ProfileState(
    val isLoading:Boolean = false,
    val userName :String = "",
    val fullName :String ="",
    val gender:String = "",
    val profileImg:String="",

)
