package com.example.chatbox.data.model

sealed class ApiResult<T> {
    data class Success<T>(val data: CommonResponse<T>) : ApiResult<T>()
    data class Error<T>(val message: String) : ApiResult<T>()
    class Empty<T> : ApiResult<T>()

}