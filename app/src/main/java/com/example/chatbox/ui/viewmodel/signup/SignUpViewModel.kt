package com.example.chatbox.ui.viewmodel.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.local.datastore.PreferenceKey
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.network.Repository
import com.example.chatbox.data.network.supabase
import com.example.chatbox.utils.CommonUtils.convertToByteArray
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreHelper: DataStoreHelper
):ViewModel() {
    private val _stateFlow = MutableStateFlow(SignUpState())
    val stateFlow: StateFlow<SignUpState> get() = _stateFlow

    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.UploadImageApi->{
                _stateFlow.value = _stateFlow.value.copy(isLoading = true)
                viewModelScope.launch {
                    try{
                        val response = supabase.storage["profile_images"].upload(
                            "${System.currentTimeMillis()}.png",
                            convertToByteArray(event.bitMap)
                        )
                        _stateFlow.value = _stateFlow.value.copy(
                            isLoading = false,
                            imageUploadResponse = "https://kdntdrzmecnmryrvqexg.supabase.co/storage/v1/object/public/profile_images//${response.path}"
                        )
                    }catch (ex:Exception){
                        Log.e("imageUrl",ex.localizedMessage?:"")
                        _stateFlow.value = _stateFlow.value.copy(
                            isLoading = false,
                        )
                    }
                }
            }
            is SignUpEvent.SignUpApiCAll->{
                _stateFlow.value = _stateFlow.value.copy(isLoading = true)
                viewModelScope.launch { 
                    val response = repository.signUpUser(event.request)
                    _stateFlow.value = _stateFlow.value.copy(
                        isLoading = false,
                        signUpApiResult = response
                    )
                    if(response is ApiResult.Success){
                        dataStoreHelper.setData(PreferenceKey.authToken,response.data.response.token)
                        dataStoreHelper.setData(PreferenceKey.fullName,response.data.response.userDetail.fullName)
                        dataStoreHelper.setData(PreferenceKey.userName,response.data.response.userDetail.userName)
                        dataStoreHelper.setData(PreferenceKey.gender,response.data.response.userDetail.gender)
                        dataStoreHelper.setData(PreferenceKey.profileImg,response.data.response.userDetail.profileImg)
                        dataStoreHelper.setData(PreferenceKey.isLogIn,true)
                    }
                }
            }
            is SignUpEvent.EmptyApiCall->{
                _stateFlow.value = _stateFlow.value.copy(
                    signUpApiResult = ApiResult.Empty()
                )
            }
            else->{}
        }
    }
}