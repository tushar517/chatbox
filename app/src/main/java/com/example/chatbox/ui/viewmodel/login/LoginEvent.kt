package com.example.chatbox.ui.viewmodel.login

sealed interface LoginEvent {
    data class LoginApiCall(val username: String, val password: String) : LoginEvent
    data object EmptyApiCall:LoginEvent
}