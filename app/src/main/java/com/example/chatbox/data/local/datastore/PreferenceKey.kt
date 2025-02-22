package com.example.chatbox.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val authToken = stringPreferencesKey("authToken")
    val userName = stringPreferencesKey("userName")
    val fullName = stringPreferencesKey("fullName")
    val profileImg = stringPreferencesKey("profileImg")
    val gender = stringPreferencesKey("gender")
    val isLogIn = booleanPreferencesKey("isLogin")
}