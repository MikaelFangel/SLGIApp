package com.slgi.slgiapp.ui

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

data class EventScreenState(
    val displayCreateDialog: Boolean = false,
    val displayDateDialog: Boolean = false,
    val displayTimeDialog: Boolean = false,
    val newEventName: String = "",
    val newEventDescription: String = "",
    val newEventTime: LocalTime = LocalTime.of(LocalTime.now().hour.plus(1).mod(24),0,0),
    val newEventDate: Timestamp = Timestamp(LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC), 0),
    val newEventImageURL: String = "",
    val newEventFireLeader: String = ""
)