package com.example.telinhas.autentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telinhas.constants.GenerationConstants
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class RecoverViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private var _authentication = MutableLiveData<Boolean>()
    val authentication: LiveData<Boolean> = _authentication

    fun verify(email: String) {

            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                _authentication.value = true
            }.addOnFailureListener { exception ->
                _message.value = when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> GenerationConstants.Exception.ALREADY_REGISTERED
                    is FirebaseNetworkException -> GenerationConstants.Exception.NO_CONNECTION
                    else -> GenerationConstants.Exception.ERROR
                }

        }

    }
}