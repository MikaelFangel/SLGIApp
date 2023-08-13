package com.slgi.slgiapp.data

import com.google.firebase.firestore.DocumentId

data class Event(
    @DocumentId val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val participants: Int = 0,
    val userParticipation: Boolean = false,
    val date: String = "",
    val time: String = "",
    val fireLeader: String ="",
)
