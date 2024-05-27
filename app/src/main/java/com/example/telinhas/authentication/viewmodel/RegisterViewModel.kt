package com.example.telinhas.authentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.domain.model.FirebaseUserModel
import com.example.telinhas.domain.usecase.RegisterUseCase
import com.example.telinhas.repository.UserPreferences
import com.example.telinhas.state.DataState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCase: RegisterUseCase
) : ViewModel() {

    private var _authentication = MutableLiveData<DataState<String>>()
    val authentication: LiveData<DataState<String>> = _authentication

    fun verifyRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            useCase.invoke(FirebaseUserModel(name = name, email = email, password = password))
                .collect {
                    _authentication.postValue(it)
                }
        }

    }
}