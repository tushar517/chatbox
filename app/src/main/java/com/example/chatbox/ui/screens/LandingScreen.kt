package com.example.chatbox.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatbox.R
import com.example.chatbox.navigation.AuthRoute
import com.example.chatbox.navigation.Home
import com.example.chatbox.navigation.Login
import com.example.chatbox.navigation.SignUp
import com.example.chatbox.ui.components.CustomButton
import com.example.chatbox.ui.theme.PurpleBg
import com.example.chatbox.ui.theme.typography
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(navHostController: NavHostController, isLogin: Boolean) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = PurpleBg
    ) {
        var startAnimation by rememberSaveable{ mutableStateOf(false) }
        LaunchedEffect(isLogin) {
            if(isLogin){
                delay(3000)
                navHostController.navigate(Home){
                    popUpTo<AuthRoute> {
                        inclusive = true
                    }
                }
            } else{
                delay(3000)
                startAnimation = true
            }
        }
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 15.dp, horizontal = 10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.landing_page_bg),
                contentDescription = "Landing page image",
                modifier = Modifier.fillMaxWidth().weight(0.5f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Simpliest\n" +
                            "Messaging App",
                    textAlign = TextAlign.Center,
                    style = typography.titleLarge,
                )
                AnimatedVisibility(
                    visible = startAnimation,
                    enter = slideInVertically(
                        animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
                        initialOffsetY = {
                            it/2
                        })
                ) {
                    Column {
                        CustomButton(
                            text = "Get Started",
                            onClick = {
                                navHostController.navigate(SignUp)
                            },
                            isEnable = true,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        Text(
                            "Already a member? Login",
                            textAlign = TextAlign.Center,
                            style = typography.bodySmall,
                            modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                                .pointerInput(
                                    Unit
                                ){
                                    detectTapGestures {
                                        navHostController.navigate(Login)
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen(rememberNavController(),true)
}