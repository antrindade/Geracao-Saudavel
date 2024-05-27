package com.example.telinhas.authentication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telinhas.domain.usecase.RecoverAuthenticationUseCase
import com.example.telinhas.state.DataState
import kotlinx.coroutines.launch

class RecoverViewModel(
    private val recoverAuthUseCase: RecoverAuthenticationUseCase
) : ViewModel() {

    private var _authentication = MutableLiveData<DataState<String>>()
    val authentication: LiveData<DataState<String>> = _authentication

    fun verify(email: String) {
        viewModelScope.launch {
            recoverAuthUseCase.invoke(email)
                .collect {
                    _authentication.postValue(it)
                }
        }
    }
}