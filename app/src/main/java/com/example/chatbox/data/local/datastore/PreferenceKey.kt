package com.example.chatbox.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val authToken = stringPreferencesKey("authToken")
}