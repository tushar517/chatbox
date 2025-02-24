package com.example.chatbox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbox.ui.theme.receiveChatBg
import com.example.chatbox.ui.theme.sentChatBg

@Composable
fun SentChatItem(content:String,modifier: Modifier) {
    Box(
        modifier = modifier

    ){
        Text(
            content,
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .padding(start = 30.dp, end = 10.dp)
                .background(
                    color = sentChatBg,
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp, bottomStart = 30.dp)
                ).padding(vertical = 12.dp, horizontal = 15.dp).align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun ReceiveChatItem(content:String,modifier: Modifier) {
    Box(
        modifier = modifier

    ){
        Text(
            content,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .padding(end = 30.dp, start = 10.dp)
                .background(
                    color = receiveChatBg,
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp, bottomEnd = 30.dp)
                ).padding(vertical = 12.dp, horizontal = 15.dp).align(Alignment.CenterStart)
        )
    }
}