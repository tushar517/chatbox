package com.example.chatbox.data.network

import com.example.chatbox.data.model.AllUserResponse
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.LoginRequest
import com.example.chatbox.data.model.LoginResponse
import com.example.chatbox.data.model.SignUpRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class Repository(
    private val httpClient: HttpClient
):RestApi {
    override suspend fun callLoginApi(commonRequest: LoginRequest): ApiResult<LoginResponse> {
        return apiCall {
            httpClient.post {
                url("auth/user/loginUser")
                setBody(commonRequest)
            }
        }
    }

    override suspend fun getAllUsers(): ApiResult<AllUserResponse> {
        return apiCall {
            httpClient.get {
                url("users")
            }
        }
    }

    override suspend fun signUpUser(request: SignUpRequest): ApiResult<LoginResponse> {
        return apiCall {
            httpClient.post {
                url("auth/user/createUser")
                setBody(request)
            }
        }
    }

}