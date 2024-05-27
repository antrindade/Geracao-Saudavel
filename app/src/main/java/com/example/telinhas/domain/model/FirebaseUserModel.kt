package com.example.telinhas.domain.model

import com.example.telinhas.extension.emptyString

data class FirebaseUserModel(
    val email: String = emptyString,
    val name: String = emptyString,
    val password: String = emptyString
)