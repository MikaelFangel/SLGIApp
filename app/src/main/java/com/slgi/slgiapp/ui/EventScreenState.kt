package com.slgi.slgiapp.ui

import com.google.firebase.Timestamp

data class EventScreenState(
    val displayCreateDialog: Boolean = false,
    val displayDateDialog: Boolean = false,
    val displayTimeDialog: Boolean = false,
    val newEventName: String = "",
    val newEventDescription: String = "",
    val newEventHours : Int? = null,
    val newEventMinutes: Int? = null,
    val newEventDate: Timestamp = Timestamp.now(),
    val newEventImageURL: String = "",
    val newEventFireLeader: String = ""
)