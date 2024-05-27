package com.example.telinhas.domain.usecase

import com.example.telinhas.domain.model.FirebaseUserModel
import com.example.telinhas.enum.RegisterEnum
import com.example.telinhas.repository.RegisterRepository
import com.example.telinhas.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class RegisterUseCase(private val repository: RegisterRepository): (FirebaseUserModel) -> Flow<DataState<String>> {
    override fun invoke(user: FirebaseUserModel) = flow {
        val result = repository.registerUser(user)
        if (result == RegisterEnum.EMAIL_SUCCESS.message) {
            emit(DataState.Success(result))
        }else {
            emit(DataState.ErrorMessage(result))
        }
    }.onStart {
        emit(DataState.Loading)
    }.catch { exception ->
        exception.printStackTrace()
        emit(DataState.ErrorMessage(exception.message ?: "Unknown error"))
    }

}