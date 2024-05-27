package com.example.telinhas.enum

import firebase.com.protolitewrapper.BuildConfig

enum class FirebaseTableEnum(val table: String) {
    User(table = if (BuildConfig.DEBUG) "UsersTesting" else "Users"),
    PartnerUser(table = "BussinessPartnerProgramUsers"),
    CurrenciesSymbols(table = "CurrenciesSymbols");
}