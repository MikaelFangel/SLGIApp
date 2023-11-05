package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

interface EventDateSourceApi {
    val events: Flow<List<Event>>

    suspend fun toggleParticipation(id: String)
    fun getParticipantFlow(eventId: String): Flow<List<User>>?
    fun getParticipants(eventId: String): Flow<List<User>>
    fun createEvent(event: Event)
    fun deleteEvent(eventId: String)
}