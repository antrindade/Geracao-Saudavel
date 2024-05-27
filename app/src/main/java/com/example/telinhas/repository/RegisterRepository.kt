package com.example.telinhas.repository

import com.example.telinhas.domain.model.FirebaseUserModel
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {

    suspend fun registerUser(userModel: FirebaseUserModel): String

    suspend fun saveUserFirebase(userModel: FirebaseUserModel)
}