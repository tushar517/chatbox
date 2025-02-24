package com.example.chatbox.ui.viewmodel.user

sealed class UserEvent{
    data object EmptyApiCall : UserEvent()

    data object GetAllUser:UserEvent()
}
