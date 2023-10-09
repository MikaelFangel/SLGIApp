package com.slgi.slgiapp.ui

import com.google.firebase.Timestamp

data class EventScreenState(
    var displayCreateDialog: Boolean = false,
    var displayDateDialog: Boolean = false,
    var displayTimeDialog: Boolean = false,
    var newEventName: String = "",
    var newEventDescription: String = "",
    var newEventHours : Int? = null,
    var newEventMinutes: Int? = null,
    var newEventDate: Timestamp = Timestamp.now(),
    var newEventImageURL: String = "",
    var newEventFireLeader: String = ""
)