package com.example.telinhas.extension

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Timestamp?.isApproved() = this?.let { date ->
    val creationLocalDate = Instant.ofEpochMilli(date.toDate().time).atZone(ZoneId.systemDefault()).toLocalDate()
    val limitDate = creationLocalDate.plusDays(7)
    val currentDate = LocalDate.now()
    currentDate.isAfter(limitDate)
} ?: false