package com.example.chatbox.ui.viewmodel.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val dataStoreHelper: DataStoreHelper,
    val repository: Repository,
    val stompClient: StompClient,
    val stateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> get() = _state

    init {
        getUserDetail()
    }

    private fun getUserDetail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                userName = dataStoreHelper.getData(PreferenceKey.userName,"").first(),
                fullName = dataStoreHelper.getData(PreferenceKey.fullName,"").first(),
                gender = dataStoreHelper.getData(PreferenceKey.gender,"").first(),
                profileImg = dataStoreHelper.getData(PreferenceKey.profileImg,"").first()
            )
        }
    }

    fun onEvent(event:ProfileEvent){
        when(event){
            else->{}
        }
    }
}