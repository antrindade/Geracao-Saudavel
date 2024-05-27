package com.example.telinhas.repository

import android.content.Context
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.domain.model.FirebaseUserModel
import com.example.telinhas.enum.RegisterEnum
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RegisterRepositoryImpl(
    private val context: Context
) : RegisterRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override suspend fun registerUser(userModel: FirebaseUserModel): String {
        return try {
            val userExists = db.collection(GenerationConstants.Firebase.COLLECTION)
                .document(userModel.email).get().await().exists()

            if (userExists) {
                RegisterEnum.ALREADY_REGISTERED.message
            } else {
                auth.createUserWithEmailAndPassword(userModel.email, userModel.password).await()
                val user = auth.currentUser
                if (user != null) {
                    saveUserFirebase(FirebaseUserModel(userModel.name, userModel.email))
                    RegisterEnum.EMAIL_SUCCESS.message
                } else {
                    RegisterEnum.ERROR.message
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseAuthWeakPasswordException -> RegisterEnum.MINIMUM_PASSWORD.message
                is FirebaseAuthInvalidCredentialsException -> RegisterEnum.VALID_EMAIL.message
                is FirebaseNetworkException -> RegisterEnum.NO_CONNECTION.message
                else -> RegisterEnum.ERROR.message
            }
        }
    }

    override suspend fun saveUserFirebase(userModel: FirebaseUserModel) {
        val shared = UserPreferences(context)
        shared.store(GenerationConstants.User.EMAIL, userModel.email)
        shared.store(GenerationConstants.User.NAME, userModel.name)
        db.collection(GenerationConstants.Firebase.COLLECTION).document(userModel.email).set(userModel).await()
    }
}