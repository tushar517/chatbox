package com.example.chatbox.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatbox.R
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.navigation.Home
import com.example.chatbox.navigation.UserDetail
import com.example.chatbox.ui.components.CustomButton
import com.example.chatbox.ui.components.CustomLoader
import com.example.chatbox.ui.components.CustomTextField
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.typography
import com.example.chatbox.ui.viewmodel.login.LoginEvent
import com.example.chatbox.ui.viewmodel.login.LoginState

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    onEvent: (LoginEvent) -> Unit,
    state:LoginState
) {
    val context = LocalContext.current
    var email by remember {
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
            email.isNotBlank() && password.isNotBlank()
        }
    }

    LaunchedEffect(state.loginApiResult) {
        when(val response = state.loginApiResult){
            is ApiResult.Success->{
                Toast.makeText(context,response.data.description,Toast.LENGTH_LONG).show()
                navHostController.navigate(Home)
            }
            is ApiResult.Empty -> {
            }
            is ApiResult.Error -> Toast.makeText(context,response.message,Toast.LENGTH_LONG).show()
        }
        onEvent(LoginEvent.EmptyApiCall)
    }
    Scaffold {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                "Login To ChatBox",
                style = typography.bodyLarge.copy(
                    color = Color.Black
                )
            )
            Text(
                "Welcome back! Sign in using your social account or email to continue us",
                style = typography.bodySmall.copy(
                    color = grey_7B,
                    textAlign = TextAlign.Center
                )
            )
            CustomTextField(
                label = "Email",
                value = email,
                keyBoardType = KeyboardType.Text,
                onValueChange = { value ->
                    email = value
                },
                modifier = Modifier.padding(top = 50.dp)
            )
            CustomTextField(
                value = password,
                onValueChange = { value ->
                    password = value
                },
                label = "Password",
                keyBoardType = KeyboardType.Password,
                modifier = Modifier.padding(top = 30.dp),
                visualTransformation = if (isPassword) VisualTransformation { value ->
                    TransformedText(
                        AnnotatedString("*".repeat(value.length)),
                        OffsetMapping.Identity
                    )
                } else {
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
                }
            )
            CustomButton(
                text = "Get Code",
                onClick = {
                    onEvent(
                        LoginEvent.LoginApiCall(
                            username = email,
                            password = password
                        )
                    )
//                    navController.navigate(Home(UserDetail(userName = employeeCode, password = password)))
                },
                modifier = Modifier.padding(top = 30.dp),
                isEnable = isButtonEnable.value
            )
        }
    }
    if (state.isLoading) {
        CustomLoader()
    }
}