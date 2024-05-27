package com.example.telinhas.repository

import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.state.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthRepository() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun verifyLogin(email: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            if (authResult.user == null) {
                emit(DataState.ErrorMessage("Authentication failed"))
                return@flow
            }

            val docRef = db.collection(GenerationConstants.Firebase.COLLECTION).document(email)
            val documentSnapshot = docRef.get().await()
            val amount = documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)

            if (amount != null) {
                emit(DataState.Success(true))
            } else {
                saveData(email)
                emit(DataState.Success(false))
            }
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(DataState.ErrorMessage(error = e.errorCode))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(DataState.ErrorMessage(e.errorCode))
        }
    }


    private fun saveData(email: String) {
        // Implement your save data logic here
    }
}