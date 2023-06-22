package com.example.telinhas.weight_check.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.telinhas.constants.GenerationConstants
import com.example.telinhas.repository.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ResultViewModel(application: Application) : AndroidViewModel(application) {

    private val bdFire = FirebaseFirestore.getInstance()

     fun saveData(waterResult: Int) {
        val shared = UserPreferences(getApplication())

        val docRef = bdFire.collection(GenerationConstants.Firebase.COLLECTION).document(shared.get(
            GenerationConstants.User.EMAIL))

        docRef.get().addOnSuccessListener { documentSnapshot ->

            val sum: Long? = documentSnapshot.getLong(GenerationConstants.Firebase.AMOUNT_OF_WATER)

            if (sum == null) {
                val updates = hashMapOf(
                    GenerationConstants.Firebase.AMOUNT_OF_WATER to waterResult
                )
                docRef.set(updates, SetOptions.merge())
            }
        }
    }
}