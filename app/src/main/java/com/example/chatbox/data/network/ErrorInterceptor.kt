package com.example.chatbox.data.network

import android.util.Log
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.CommonResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode


    suspend inline fun<reified T : Any> apiCall(httpRequest:suspend ()->HttpResponse):ApiResult<T>{
        try{
            val response = httpRequest()
            return when (response.status) {
                HttpStatusCode.OK -> ApiResult.Success(response.body<CommonResponse<T>>())
                HttpStatusCode.Forbidden->ApiResult.Error("Invalid Password")
                else -> ApiResult.Error<T>("Something went wrong")
            }
        }catch (ex:Exception){
            Log.e("request error",ex.localizedMessage?:"")
            return ApiResult.Error<T>("Something went wrong")
        }
    }
