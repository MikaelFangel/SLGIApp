package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Event(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val dateAndTime: Timestamp = Timestamp(0, 0),
    val fireleader: String = "",
)
