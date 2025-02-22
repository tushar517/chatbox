package com.example.chatbox.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


object CommonUtils {
    fun bitmapToBase64(bitmap: Bitmap):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    fun convertToByteArray(image: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        image.compress(
            Bitmap.CompressFormat.JPEG,
            60,
            baos
        )
        var options = 90
        while (baos.toByteArray().size / 1024 > 400) {
            baos.reset()
            image.compress(
                Bitmap.CompressFormat.JPEG,
                options,
                baos
            )
            options -= 10
        }
        return baos.toByteArray()
    }
}