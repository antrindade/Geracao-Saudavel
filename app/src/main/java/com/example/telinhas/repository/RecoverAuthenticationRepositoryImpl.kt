package com.example.telinhas.repository

import com.example.telinhas.enum.RecoverEnum
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RecoverAuthenticationRepositoryImpl : RecoverAuthenticationRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun authenticationEmailValidity(email: String): Flow<String> = flow {
        try {
            auth.sendPasswordResetEmail(email).await()
            emit(RecoverEnum.EMAIL_SEND_SUCCESSFULLY.message)
        } catch (exception: Exception) {
            val errorMessage = when (exception) {
                is FirebaseAuthInvalidCredentialsException -> RecoverEnum.AUTH_INVALID_CREDENTIALS.message
                is FirebaseNetworkException -> RecoverEnum.NETWORK_EXCEPTION.message
                else -> RecoverEnum.GENERATION_EXCEPTION.message
            }
            emit(errorMessage)
        }
    }
}