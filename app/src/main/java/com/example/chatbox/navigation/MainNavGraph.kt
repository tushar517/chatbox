package com.example.chatbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatbox.ui.screens.HomeScreen
import com.example.chatbox.ui.screens.LandingScreen
import com.example.chatbox.ui.screens.LoginScreen
import com.example.chatbox.ui.viewmodel.home.HomeViewModel
import com.example.chatbox.ui.viewmodel.login.LoginViewModel

@Composable
fun MainNavGraph(mainNavController: NavHostController){
    NavHost(
        navController = mainNavController,
        startDestination = Landing
    ){
        composable<Landing> {
            LandingScreen()
        }
        composable<Login> {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navHostController = mainNavController,
                onEvent = loginViewModel::onEvent,
                state = loginViewModel.stateFlow.collectAsState().value
            )
        }

        composable<Home> {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                mainNavHostController = mainNavController,
                onEvent = homeViewModel::onEvent,
                state = homeViewModel.stateFlow.collectAsState().value
            )
        }
    }
}