package com.example.chatbox.ui.viewmodel.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.AllUser
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreHelper: DataStoreHelper,
    private val stomp: StompClient
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state
        .onStart {
            onEvent(UserEvent.GetAllUser)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserState()
        )

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.GetAllUser -> {
                _state.value = _state.value.copy(isLoading = true)
                viewModelScope.launch {
                    val response = repository.getAllUsers()
                    if(response is ApiResult.Success){
                        _state.value = _state.value.copy(
                            isLoading = false,
                            allUserApiResult = response,
                            userList = response.data.response.allUsers,
                            userName = getUserName()
                        )
                    }else {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            allUserApiResult = response,
                            userName = getUserName()
                        )
                    }
                }
                val compositeDisposable = CompositeDisposable()
                stomp.connect()
                compositeDisposable.add(stomp
                    .topic("/topic/connectedUser")
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                        {
                            val userList:MutableList<AllUser> = _state.value.userList.toMutableList()
                            try {
                                val newUser = Json.decodeFromString<AllUser>(it.payload)
                                if(!(userList.map { it.userName }.contains(newUser.userName))) {
                                    userList.add(newUser)
                                }
                            }catch (ex:Exception){
                                Log.e("UserSocketMessage","${ex.localizedMessage?:""}")
                            }
                            _state.value = _state.value.copy(
                                userList = userList
                            )
                        },
                        {
                            Log.e("UserSocketMessage",it.localizedMessage?:"")
                        }
                    ))
            }
            is UserEvent.EmptyApiCall->{
                _state.value = _state.value.copy(
                    allUserApiResult = ApiResult.Empty()
                )
            }
        }
    }

    private suspend fun getUserName(): String {
        return withContext(Dispatchers.IO){ dataStoreHelper.getData(PreferenceKey.userName, "").first() }
    }
}