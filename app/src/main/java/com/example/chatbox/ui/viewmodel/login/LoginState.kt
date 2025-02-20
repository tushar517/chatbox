package com.example.chatbox.ui.viewmodel.login

import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.LoginResponse

data class LoginState(
    var isLoading:Boolean = false,
    var loginApiResult: ApiResult<LoginResponse> = ApiResult.Empty()
)