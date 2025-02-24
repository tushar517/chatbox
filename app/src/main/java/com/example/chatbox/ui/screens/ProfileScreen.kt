package com.example.chatbox.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.chatbox.R
import com.example.chatbox.navigation.EditDetail
import com.example.chatbox.ui.components.CustomButton
import com.example.chatbox.ui.theme.PurpleBg
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.typography
import com.example.chatbox.ui.viewmodel.profile.ProfileEvent
import com.example.chatbox.ui.viewmodel.profile.ProfileState

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PurpleBg),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = state.profileImg,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Transparent, CircleShape),
            error = painterResource(if (state.gender == "Male") R.drawable.male_profile_placeholder else R.drawable.female_profile_placeholder),
            placeholder = painterResource(if (state.gender == "Male") R.drawable.male_profile_placeholder else R.drawable.female_profile_placeholder)
        )
        Text(
            state.fullName,
            style = typography.bodyMedium.copy(
                color = White
            ),
            modifier = Modifier.padding(top = 20.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .border(
                    width = 1.dp,
                    color = grey_7B,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                state.userName,
                style = typography.bodyMedium.copy(
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                state.gender,
                style = typography.bodyMedium.copy(
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 20.dp)
            )
            CustomButton(
                modifier = Modifier.padding(20.dp),
                text = "Edit Profile",
                isEnable = true,
                onClick = {
                    navHostController.navigate(EditDetail)
                }
            )
        }

    }
}