package com.example.chatbox.ui.theme

import android.graphics.LinearGradient
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val grey_7B = Color(0xff797C7B)
val green = Color(0xff24786D)
val grey_10 = Color(0xffCDD1D0)
val PurpleBg = Color(0xFF4A3F69)
val White = Color(0xFFFFFFFF)
val Transparent = Color(0x00000000)
val sentChatBg = Color(0xFFEBEAEA)
val receiveChatBg = Color(0xFF9747FF)
val nextButtonBg = Brush.linearGradient(
    colors = listOf(Color(0xFFF97794),Color(0xFF623AA2))
)
val disableNextButtonBg = Brush.linearGradient(
    colors = listOf(grey_10,grey_7B)
)