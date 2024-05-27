package com.example.telinhas.enum

enum class RegisterEnum(val message: String) {
    EMAIL_SUCCESS("Email cadastrado com sucesso"),
    EMPTY_FIELD ("Preencha todos os campos"),
    MINIMUM_PASSWORD ("Digite uma senha com no minimo 6 caractere"),
    VALID_EMAIL ("Email invalido!"),
    ALREADY_REGISTERED ("Email já cadastrado!"),
    NO_CONNECTION ("Sem conexão com a internet"),
    ERROR ("Erro ao entrar com usuario"),
}