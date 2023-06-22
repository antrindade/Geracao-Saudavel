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

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private var _authentication = MutableLiveData<Boolean>()
    val authentication: LiveData<Boolean> = _authentication

    fun verifyLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authentication ->
            val docRef =
                db.collection(GenerationConstants.Firebase.COLLECTION).document(email)

            docRef.get().addOnSuccessListener { documentSnapshot ->
                val amount = documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)

                if (authentication.isSuccessful) {
                    if (amount != null) {
                        _authentication.value = true
                    }else {
                        saveData(email)
                        _authentication.value = false
                    }
                }

            }
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

    private fun saveData(email: String) {
        val shared = UserPreferences(getApplication<Application>().applicationContext)
        shared.store(GenerationConstants.User.EMAIL, email)
    }

}