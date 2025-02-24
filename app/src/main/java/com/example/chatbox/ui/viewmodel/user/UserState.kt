package com.example.chatbox.ui.viewmodel.user

import com.example.chatbox.data.model.AllUser
import com.example.chatbox.data.model.AllUserResponse
import com.example.chatbox.data.model.ApiResult

data class UserState(
    val isLoading:Boolean = false,
    val allUserApiResult:ApiResult<AllUserResponse> = ApiResult.Empty(),
    val userName:String = "",
    val userList:List<AllUser>  = listOf()
)