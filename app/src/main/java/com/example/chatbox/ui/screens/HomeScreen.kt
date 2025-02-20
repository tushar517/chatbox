package com.example.chatbox.ui.screens

import android.R.id
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.chatbox.ui.components.CustomButton
import com.example.chatbox.ui.viewmodel.home.HomeEvent
import com.example.chatbox.ui.viewmodel.home.HomeState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.dto.LifecycleEvent


@Composable
fun HomeScreen(
    mainNavHostController: NavHostController,
    onEvent: (HomeEvent) -> Unit,
    state: HomeState
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Center
        ) {
            CustomButton(
                "Send",
                {
                    onEvent(HomeEvent.SendMessage)
                },
                true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}