package com.example.telinhas.authentication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telinhas.domain.usecase.VerifyLoginUseCase
import com.example.telinhas.state.DataState
import kotlinx.coroutines.launch

class LoginViewModel(private val verifyLoginUseCase: VerifyLoginUseCase) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _authentication = MutableLiveData<DataState<Boolean>>()
    val authentication: LiveData<DataState<Boolean>> = _authentication

    fun verifyLogin(email: String, password: String) {
        viewModelScope.launch {
            verifyLoginUseCase(email, password)
                .collect {
                _authentication.postValue(it)
            }
        }
    }

    private fun saveData(email: String) {
        //val shared = UserPreferences(getApplication<Application>().applicationContext)
        //shared.store(GenerationConstants.User.EMAIL, email)
    }

}