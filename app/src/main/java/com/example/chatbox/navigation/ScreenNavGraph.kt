package com.example.chatbox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chatbox.data.model.AllUser
import com.example.chatbox.ui.screens.AllUserScreen
import com.example.chatbox.ui.screens.ChatScreen
import com.example.chatbox.ui.screens.EditProfileScreen
import com.example.chatbox.ui.screens.ProfileScreen
import com.example.chatbox.ui.viewmodel.chat.ChatViewModel
import com.example.chatbox.ui.viewmodel.profile.ProfileViewModel
import com.example.chatbox.ui.viewmodel.user.UserViewModel
import kotlin.reflect.typeOf

@Composable
fun ChatNavGraph() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = User
    ) {
        composable<User> {
            val viewModel = hiltViewModel<UserViewModel>()
            AllUserScreen(
                navHostController,
                viewModel::onEvent,
                viewModel.state.collectAsState().value
            )
        }

        composable<Chat> {entry->
            val viewModel = hiltViewModel<ChatViewModel>()
            ChatScreen(
                navHostController,
                viewModel::onEvent,
                viewModel.state.collectAsState().value
            )
        }
    }
}

@Composable
fun SettingNavGraph(navHostController: NavHostController, mainNavHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Profile
    ) {
        composable<Profile> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                navHostController = navHostController,
                state = viewModel.state.collectAsState().value,
                onEvent = viewModel::onEvent
            )
        }

        composable<EditDetail> {
            EditProfileScreen()
        }
    }
}
