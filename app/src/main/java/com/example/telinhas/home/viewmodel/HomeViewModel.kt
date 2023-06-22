package com.example.telinhas.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    fun saveCloudFire(sum: Long, drank: Long) {

        val shared = UserPreferences(getApplication())

        db.collection(GenerationConstants.Firebase.COLLECTION)
            .document(shared.get(GenerationConstants.User.EMAIL))
            .update(GenerationConstants.Firebase.SUM, sum)

        db.collection(GenerationConstants.Firebase.COLLECTION)
            .document(shared.get(GenerationConstants.User.EMAIL))
            .update(GenerationConstants.Firebase.DRANK, drank)
    }

}