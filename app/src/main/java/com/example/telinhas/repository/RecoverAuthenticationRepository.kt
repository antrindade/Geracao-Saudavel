package com.example.telinhas.repository

import com.example.telinhas.state.DataState
import kotlinx.coroutines.flow.Flow

interface RecoverAuthenticationRepository {
    suspend fun authenticationEmailValidity(email: String): Flow<String>
}