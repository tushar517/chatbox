package com.example.chatbox.ui.viewmodel.signup

import android.graphics.Bitmap
import com.example.chatbox.data.model.SignUpRequest

sealed interface SignUpEvent {
    data object Loading:SignUpEvent
    data object EmptyApiCall:SignUpEvent
    data class SignUpApiCAll(val request:SignUpRequest):SignUpEvent
    data class UploadImageApi(val bitMap:Bitmap):SignUpEvent
}