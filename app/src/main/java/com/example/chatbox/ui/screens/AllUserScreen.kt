package com.example.chatbox.ui.screens

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.navigation.Chat
import com.example.chatbox.ui.components.CustomLoader
import com.example.chatbox.ui.components.UserListItem
import com.example.chatbox.ui.viewmodel.user.UserEvent
import com.example.chatbox.ui.viewmodel.user.UserState

@Composable
fun AllUserScreen(
    navHostController: NavHostController,
    onEvent: (UserEvent) -> Unit,
    state: UserState
) {
    LaunchedEffect(state.allUserApiResult) {

        when (val response = state.allUserApiResult) {
            is ApiResult.Empty -> {}
            is ApiResult.Error -> {
            }
            is ApiResult.Success -> {
            }
        }
        onEvent(UserEvent.EmptyApiCall)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

    ) {
        items(
            state.userList,
            key = { user -> user.userName }
        ) {user->
            if(user.userName != state.userName)
            UserListItem(user = user, modifier = Modifier.pointerInput(Unit) {
                detectTapGestures {
                    try{
                        navHostController.navigate(Chat(
                            userName = user.userName,
                            gender = user.gender,
                            fullName = user.fullName,
                            lastSeen = user.lastSeen,
                            profileImg = user.profileImg
                        ))
                    }catch (ex:Exception){
                        Log.e("error",ex.localizedMessage?:"")
                    }
                }
            })
        }
    }
    if (state.isLoading) {
        CustomLoader()
    }


}