package com.example.chatbox.ui.viewmodel.signup

import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.LoginResponse

data class SignUpState(
    val isLoading:Boolean = false,
    val signUpApiResult:ApiResult<LoginResponse> = ApiResult.Empty(),
    val imageUploadResponse:String = ""
)