package com.example.telinhas.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.telinhas.constants.GenerationConstants

class UserPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(GenerationConstants.ShearedPreference.NAME, Context.MODE_PRIVATE)

    fun store(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun get(key: String): String {
        return preferences.getString(key, "") ?: ""
    }
}