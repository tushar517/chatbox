package com.example.chatbox.ui.viewmodel.home

sealed interface HomeEvent{
    data object callAllUserApi:HomeEvent
    data object getAuthtoken:HomeEvent
    data object SendMessage:HomeEvent
}