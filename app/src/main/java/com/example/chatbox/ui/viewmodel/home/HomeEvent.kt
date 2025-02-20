package com.example.chatbox.ui.viewmodel.home

sealed interface HomeEvent{
    data object SubscribeToSocket:HomeEvent
    data object callAllUserApi:HomeEvent
    data object SendMessage:HomeEvent
}