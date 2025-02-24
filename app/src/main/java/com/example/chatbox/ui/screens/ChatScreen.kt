package com.example.chatbox.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatbox.ui.components.ReceiveChatItem
import com.example.chatbox.ui.components.SentChatItem
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.sentChatBg
import com.example.chatbox.ui.viewmodel.chat.ChatEvent
import com.example.chatbox.ui.viewmodel.chat.ChatState

@Composable
fun ChatScreen(navController: NavHostController, onEvent: (ChatEvent) -> Unit, state: ChatState) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
                .fillMaxWidth()
        ){
            items(state.allChats){chat->
                if(chat.senderId == state.senderUserName){
                    SentChatItem(chat.content,Modifier.align(Alignment.End).fillMaxWidth())
                }else{
                    ReceiveChatItem(chat.content,Modifier.align(Alignment.Start).fillMaxWidth())
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .padding(top = 20.dp, end = 20.dp)
                    .background(color = sentChatBg, shape = RoundedCornerShape(40.dp))
                    .weight(1f),
                onValueChange = {
                    onEvent(ChatEvent.OnMessageChange(it))
                },
                value = state.message,
                colors = TextFieldDefaults.colors(
                     focusedTextColor = Color.Black
                ),
            )
            Box(
                Modifier
                    .size(40.dp)
                    .background(Color.Green, shape = CircleShape)
                    .clip(CircleShape)
                    .pointerInput(Unit){
                        detectTapGestures {
                            onEvent(ChatEvent.SendMessage(state.message))
                            onEvent(ChatEvent.OnMessageChange(""))
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier.size(30.dp).align(Alignment.Center)
                )
            }
        }
    }
}