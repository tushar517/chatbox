package com.example.chatbox.ui.viewmodel.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    val dataStoreHelper: DataStoreHelper
):ViewModel() {
    private val _stateFlow = MutableStateFlow(false)
    val stateFlow:StateFlow<Boolean> get() = _stateFlow
        .onStart {
            _stateFlow.value = dataStoreHelper.getData(PreferenceKey.isLogIn,false).first()
        }
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )


}