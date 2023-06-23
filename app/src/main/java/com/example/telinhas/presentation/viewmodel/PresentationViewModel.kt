package com.example.telinhas.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telinhas.authentication.helper.BiometricHelper
import com.google.firebase.auth.FirebaseAuth

class PresentationViewModel(application: Application) : AndroidViewModel(application) {

    private val _loggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = _loggedUser

    fun verifyAuthentication() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            _loggedUser.value =
                (BiometricHelper.isBiometricAvailable(getApplication<Application>().applicationContext))
        }
    }

}