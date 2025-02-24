package com.example.chatbox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.chatbox.R
import com.example.chatbox.data.model.AllUser
import com.example.chatbox.ui.theme.typography

@Composable
fun UserListItem(
        modifier: Modifier = Modifier,
        user:AllUser,
    ) {
    Row(
        modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = user.profileImg,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Transparent, CircleShape),
            error = painterResource(if(user.gender == "Male") R.drawable.male_profile_placeholder else R.drawable.female_profile_placeholder),
            placeholder = painterResource(if(user.gender == "Male") R.drawable.male_profile_placeholder else R.drawable.female_profile_placeholder))

        Text(user.fullName,modifier=Modifier.weight(1f),
            style = typography.bodySmall.copy(
                color = Color.Black
            ))
    }
}