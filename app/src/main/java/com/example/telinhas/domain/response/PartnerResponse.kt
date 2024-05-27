package com.example.telinhas.domain.response

import com.example.telinhas.extension.emptyString
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

@IgnoreExtraProperties
data class PartnerResponse(
    @SerializedName("email") @get:PropertyName("email") @set:PropertyName("email")
    var email: String? = emptyString,

    @SerializedName("name") @get:PropertyName("name") @set:PropertyName("name")
    var name: String? = emptyString,
): Serializable