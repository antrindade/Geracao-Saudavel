package com.example.telinhas.domain.usecase

import com.example.telinhas.enum.RecoverEnum
import com.example.telinhas.repository.RecoverAuthenticationRepository
import com.example.telinhas.state.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class RecoverAuthenticationUseCase(
    private val repository: RecoverAuthenticationRepository,
    private val dispatcher: CoroutineDispatcher
) : (String) -> Flow<DataState<String>> {
    override fun invoke(email: String) = flow {
        repository.authenticationEmailValidity(email).collect { message ->
            if (message == RecoverEnum.EMAIL_SEND_SUCCESSFULLY.message) {
                emit(DataState.Success(message))
            } else {
                emit(DataState.ErrorMessage(message))
            }
        }
    }.onStart {
        emit(DataState.Loading)
    }.catch { exception ->
        exception.printStackTrace()
        emit(DataState.ErrorMessage(exception.message ?: "Unknown error"))
    }.flowOn(dispatcher)
}