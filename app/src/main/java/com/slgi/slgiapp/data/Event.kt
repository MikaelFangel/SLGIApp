package com.slgi.slgiapp.data

data class Event(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val participants: Int,
    val userParticipation: Boolean,
    val date: String,
    val time: String,
    val fireLeader: String,
)
