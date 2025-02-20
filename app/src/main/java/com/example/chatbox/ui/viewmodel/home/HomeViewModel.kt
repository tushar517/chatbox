package com.example.chatbox.ui.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.UserModel
import com.example.chatbox.data.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.Stomp
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreHelper: DataStoreHelper
):ViewModel() {
    private val _stateFlow = MutableStateFlow(HomeState())
    val stateFlow get() = _stateFlow.onStart {
        _stateFlow.value = _stateFlow.value.copy(
            authToken = runBlocking {  dataStoreHelper.getData(PreferenceKey.authToken,"").first()}
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),HomeState())

    fun onEvent(event:HomeEvent){
        when(event){
            is HomeEvent.callAllUserApi -> {
                viewModelScope.launch {
                    repository.getAllUsers()
                }
            }
            is HomeEvent.getAuthtoken->{
                val authToken = runBlocking {  dataStoreHelper.getData(PreferenceKey.authToken,"").first()}
                _stateFlow.value = _stateFlow.value.copy(
                    authToken = authToken
                )
            }
            is HomeEvent.SendMessage->{
                val compositeDisposable = CompositeDisposable()
                val stomp = Stomp.over(
                    Stomp.ConnectionProvider.OKHTTP,
                    "ws://192.168.1.3:8088/ws/websocket",
                    mapOf(
                        "Authorization" to "Bearer ${_stateFlow.value.authToken}"
                    )
                )
                val send =stomp.send("/user/connectedUser", Json.encodeToString(UserModel()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({

                },{ Log.e("SendSocketError",it.localizedMessage?:"")})
                compositeDisposable.add(send)
            }
            else -> {}
        }
    }
}