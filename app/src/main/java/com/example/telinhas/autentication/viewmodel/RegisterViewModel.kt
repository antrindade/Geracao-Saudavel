package com.example.telinhas.autentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private var _authentication = MutableLiveData<Boolean>()
    val authentication: LiveData<Boolean> = _authentication

    fun verifyRegister(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            saveData(name, email)
            _authentication.value = true
        }.addOnFailureListener { exception ->
            _message.value = when (exception) {
                is FirebaseAuthWeakPasswordException -> GenerationConstants.Exception.MINIMUM_PASSWORD
                is FirebaseAuthInvalidCredentialsException -> GenerationConstants.Exception.VALID_EMAIL
                is FirebaseAuthUserCollisionException -> GenerationConstants.Exception.ALREADY_REGISTERED
                is FirebaseNetworkException -> GenerationConstants.Exception.NO_CONNECTION
                else -> GenerationConstants.Exception.ERROR
            }
        }
    }

    private fun saveData(name: String, email: String) {
        val shared = UserPreferences(getApplication())
        shared.store(GenerationConstants.User.EMAIL, email)
        shared.store(GenerationConstants.User.NAME, name)
        val documentUser = hashMapOf(
            GenerationConstants.User.NAME to name,
            GenerationConstants.User.EMAIL to email
        )
        db.collection(GenerationConstants.Firebase.COLLECTION).document(email)
            .set(documentUser)
    }
}