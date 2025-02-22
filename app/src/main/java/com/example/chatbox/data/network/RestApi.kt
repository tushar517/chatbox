package com.example.chatbox.data.network

import com.example.chatbox.data.model.AllUserResponse
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.LoginRequest
import com.example.chatbox.data.model.LoginResponse
import com.example.chatbox.data.model.SignUpRequest
import okhttp3.RequestBody

interface RestApi {

    suspend fun callLoginApi(commonRequest: LoginRequest): ApiResult<LoginResponse>

    suspend fun getAllUsers():ApiResult<AllUserResponse>

    suspend fun signUpUser(request: SignUpRequest):ApiResult<LoginResponse>
}
