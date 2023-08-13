package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

interface EventDateSourceApi {
    val events: Flow<List<Event>>

    suspend fun toggleParticipation(id: String)
    suspend fun isParticipating(id: String): Boolean
}