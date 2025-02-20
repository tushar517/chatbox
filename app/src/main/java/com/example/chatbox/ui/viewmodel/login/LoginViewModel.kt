package com.example.chatbox.ui.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.LoginRequest
import com.example.chatbox.data.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreHelper: DataStoreHelper
):ViewModel(){
    private val _stateFlow = MutableStateFlow(LoginState());
    val stateFlow:StateFlow<LoginState> get() = _stateFlow

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.LoginApiCall -> {
                val request = LoginRequest(username = event.username, password = event.password)
                viewModelScope.launch {
                    _stateFlow.value = _stateFlow.value.copy(isLoading = true)
                    val result = repository.callLoginApi(request)
                    if (result is ApiResult.Success){
                        dataStoreHelper.setData(PreferenceKey.authToken,result.data.response.token)
                    }
                    _stateFlow.value = _stateFlow.value.copy(isLoading = false, loginApiResult = result)
                }
            }
            is LoginEvent.EmptyApiCall -> {
                _stateFlow.value = _stateFlow.value.copy(loginApiResult = ApiResult.Empty())
            }
        }
    }
}