package com.example.telinhas.weight_check.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class WeightViewModel(application: Application) : AndroidViewModel(application) {

    private val bdFire = FirebaseFirestore.getInstance()

    fun saveData(kilogram: String) {

        val shared = UserPreferences(getApplication())
        shared.store(GenerationConstants.User.KILOGRAM, kilogram)

        val docRef = bdFire.collection(GenerationConstants.Firebase.COLLECTION).document(shared.get(
            GenerationConstants.User.EMAIL))

        val updates = hashMapOf(
            GenerationConstants.User.KILOGRAM to kilogram
        )

        docRef.set(updates, SetOptions.merge())

    }
}