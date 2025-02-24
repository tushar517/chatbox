package com.example.chatbox.ui.screens

import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatbox.R
import com.example.chatbox.navigation.ChatNavGraph
import com.example.chatbox.navigation.SettingNavGraph
import com.example.chatbox.ui.theme.Purple80
import com.example.chatbox.ui.theme.PurpleBg
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.nextButtonBg
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    mainNavHostController: NavHostController,
) {
    var selectedItem by remember {
        mutableIntStateOf(1)
    }
    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Purple80,
                        shape = RoundedCornerShape(55.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = grey_7B,
                        shape = RoundedCornerShape(55.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = if (selectedItem == 1) R.drawable.selected_chat_bottom_nav else R.drawable.unselected_chat_bottom_nav),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            selectedItem = 1
                            coroutineScope.launch {
                                pagerState.scrollToPage(0)
                            }
                        }
                        .padding(10.dp)
                )
                Image(
                    painter = painterResource(id = if (selectedItem == 2) R.drawable.selected_setting_bottom_nav else R.drawable.unselected_setting_bottom_nav),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            selectedItem = 2
                            coroutineScope.launch {
                                pagerState.scrollToPage(1)
                            }
                        }
                        .padding(10.dp)
                )
            }
        }
    ) {
        HorizontalPager(
            pagerState,
            userScrollEnabled = false,
            modifier = Modifier.padding(it).fillMaxSize()
        ) {page->
            if(page == 0){
                ChatNavGraph()
            }else{
                val settingNavController = rememberNavController()

                SettingNavGraph(settingNavController,mainNavHostController)
            }
        }
    }
}