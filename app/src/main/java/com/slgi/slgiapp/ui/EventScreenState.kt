package com.slgi.slgiapp.ui

import com.google.firebase.Timestamp
import java.sql.Time

data class EventScreenState(
    var displayCreateDialog: Boolean = false,
    var displayDateDialog: Boolean = false,
    var displayTimeDialog: Boolean = false,
    var newEventName: String = "",
    var newEventDescription: String = "",
    var newEventTime : Time = Time(10,0,0),
    var newEventDate: Timestamp = Timestamp.now(),
    var newEventImageURL: String = "",
    var newEventFireLeader: String = ""
)