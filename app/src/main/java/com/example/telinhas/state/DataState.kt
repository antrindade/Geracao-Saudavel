package com.example.telinhas.state

sealed class DataState<out T> where T : Any? {
    data object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()

    data class ErrorMessage(val error: String) : DataState<Nothing>()
}

interface ErrorType