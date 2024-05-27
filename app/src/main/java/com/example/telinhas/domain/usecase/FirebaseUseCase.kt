package com.example.telinhas.domain.usecase

import com.example.telinhas.repository.AuthRepository
import com.example.telinhas.state.DataState
import kotlinx.coroutines.flow.Flow

class VerifyLoginUseCase(private val authRepository: AuthRepository) : (String, String) -> Flow<DataState<Boolean>> {
    override operator fun invoke(email: String, password: String): Flow<DataState<Boolean>> {
        return authRepository.verifyLogin(email, password)
    }
}