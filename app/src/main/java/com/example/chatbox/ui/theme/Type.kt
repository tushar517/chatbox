package com.example.chatbox.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val typography = Typography(
    bodySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = White
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        color = White
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 21.sp,
        color = White
    ),
    titleLarge = TextStyle(
        color = White,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)