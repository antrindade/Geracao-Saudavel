package com.example.telinhas.home.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(
    private val context: Context
    ) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun saveCloudFire(sum: Long, drank: Long) {

        val shared = UserPreferences(context)

        db.collection(GenerationConstants.Firebase.COLLECTION)
            .document(shared.get(GenerationConstants.User.EMAIL))
            .update(GenerationConstants.Firebase.SUM, sum)

        db.collection(GenerationConstants.Firebase.COLLECTION)
            .document(shared.get(GenerationConstants.User.EMAIL))
            .update(GenerationConstants.Firebase.DRANK, drank)
    }

}