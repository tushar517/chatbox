package com.example.chatbox.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatbox.R
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.navigation.AuthRoute
import com.example.chatbox.navigation.Home
import com.example.chatbox.ui.components.CustomLoader
import com.example.chatbox.ui.components.CustomTextField
import com.example.chatbox.ui.components.LoginSignUpButton
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.nextButtonBg
import com.example.chatbox.ui.theme.typography
import com.example.chatbox.ui.viewmodel.login.LoginEvent
import com.example.chatbox.ui.viewmodel.login.LoginState
import kotlin.math.roundToInt

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    onEvent: (LoginEvent) -> Unit,
    state: LoginState
) {
    val context = LocalContext.current
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPassword by remember {
        mutableStateOf(true)
    }
    val isButtonEnable = remember {
        derivedStateOf {
            userName.isNotBlank() && password.isNotBlank()
        }
    }

    var isAnimate by remember { mutableStateOf(false) }

    LaunchedEffect(state.loginApiResult) {
        when (val response = state.loginApiResult) {
            is ApiResult.Success -> {
                Toast.makeText(context, response.data.description, Toast.LENGTH_LONG).show()
                navHostController.navigate(Home){
                    popUpTo<AuthRoute> {
                        inclusive = true
                    }
                }
            }

            is ApiResult.Empty -> {
            }

            is ApiResult.Error -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                .show()
        }
        onEvent(LoginEvent.EmptyApiCall)
    }

    LaunchedEffect(Unit) {
        isAnimate = true
    }
    Scaffold(containerColor = White) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                isAnimate,
                enter = slideInVertically(
                    animationSpec = tween(
                        1500
                    )
                ),
                modifier = Modifier.weight(.2f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_login_1),
                    contentDescription = "Landing page image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login To ChatBox",
                    style = typography.bodyLarge.copy(
                        color = Color.Black
                    )
                )
                Text(
                    "Welcome back! Log in using your account to continue",
                    style = typography.bodySmall.copy(
                        color = grey_7B,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )
                CustomTextField(
                    label = "Username",
                    value = userName,
                    keyBoardType = KeyboardType.Text,
                    onValueChange = { value ->
                        userName = value
                    },
                    modifier = Modifier.padding(top = 50.dp),
                    leadingIcon = R.drawable.ic_profile
                )
                CustomTextField(
                    value = password,
                    onValueChange = { value ->
                        password = value
                    },
                    label = "Password",
                    keyBoardType = KeyboardType.Password,
                    modifier = Modifier.padding(top = 30.dp),
                    visualTransformation = if (isPassword) PasswordVisualTransformation('*') else {
                        VisualTransformation.None
                    },
                    icon = {
                        IconButton(
                            onClick = { isPassword = !isPassword },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            if (isPassword) {
                                Image(
                                    painter = painterResource(R.drawable.ic_password_off),
                                    contentDescription = "",
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.ic_password_on),
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    leadingIcon = R.drawable.ic_password
                )
                Text(
                    "Forgot your password?",
                    style = typography.titleSmall.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.End
                )
            }

            Row(
                modifier = Modifier.weight(.3f),
                horizontalArrangement = Arrangement.Start
            ) {
                AnimatedVisibility(
                    isAnimate,
                    enter = slideInHorizontally(
                        animationSpec = tween(
                            1500
                        )
                    ),
                ) {
                    Image(painter = painterResource(R.drawable.ic_login_2), contentDescription = "")
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    LoginSignUpButton(btnText = "Log In", isEnable = isButtonEnable.value) {
                        onEvent(LoginEvent.LoginApiCall(userName,password))
                    }
                    Text(
                        "Don\'t have an account? Create",
                        style = typography.titleSmall.copy(
                            fontSize = 16.sp
                        )
                    )

                }
            }


        }
    }
    if (state.isLoading) {
        CustomLoader()
    }
}

@Preview
@Composable
fun LogiinPreview(modifier: Modifier = Modifier) {
    LoginScreen(
        rememberNavController(),
        onEvent = {},
        state = LoginState()
    )
}