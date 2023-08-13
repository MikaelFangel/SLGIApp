package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Event(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val participants: Long = 0,
    val userParticipation: Boolean = false,
    val dateAndTime: Timestamp = Timestamp(0, 0),
    val fireleader: String = "",
)
