package com.example.telinhas.enum

enum class ExceptionEnum(val message: String) {
    ERROR_INVALID_EMAIL(message = "Email invalido!"),
    ERROR_WRONG_PASSWORD(message = "Digite uma senha com no minimo 6 caractere"),
    ERROR_USER_NOT_FOUND(message = "Usuario não existe")
}