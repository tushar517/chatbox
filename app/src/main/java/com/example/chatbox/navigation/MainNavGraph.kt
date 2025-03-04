package com.example.chatbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.chatbox.ui.screens.HomeScreen
import com.example.chatbox.ui.screens.LandingScreen
import com.example.chatbox.ui.screens.LoginScreen
import com.example.chatbox.ui.screens.SignUpScreen
import com.example.chatbox.ui.viewmodel.home.HomeViewModel
import com.example.chatbox.ui.viewmodel.landing.LandingViewModel
import com.example.chatbox.ui.viewmodel.login.LoginViewModel
import com.example.chatbox.ui.viewmodel.signup.SignUpViewModel

@Composable
fun MainNavGraph(mainNavController: NavHostController){
    NavHost(
        navController = mainNavController,
        startDestination = AuthRoute
    ){
        navigation<AuthRoute>(
            startDestination = Landing
        ) {
            composable<Landing> {
                val landingViewModel = hiltViewModel<LandingViewModel>()
                LandingScreen(
                    navHostController = mainNavController,
                    isLogin = landingViewModel.stateFlow.collectAsState().value
                )
            }
            composable<Login> {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    navHostController = mainNavController,
                    onEvent = loginViewModel::onEvent,
                    state = loginViewModel.stateFlow.collectAsState().value
                )
            }

            composable<SignUp> {
                val signUpViewModel = hiltViewModel<SignUpViewModel>()
                SignUpScreen(
                    navHostController = mainNavController,
                    onEvent = signUpViewModel::onEvent,
                    state = signUpViewModel.stateFlow.collectAsState().value
                )
            }
        }
        composable<Home> {
            HomeScreen(
                mainNavHostController = mainNavController,
            )
        }
    }
}