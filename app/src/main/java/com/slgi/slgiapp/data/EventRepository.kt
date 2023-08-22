package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val eventNetworkDataSource: EventDateSourceApi
) {
    val events: Flow<List<Event>>
        get() = eventNetworkDataSource.events

    suspend fun toggleParticipation(id: String) = eventNetworkDataSource.toggleParticipation(id)
    fun getParticipantFlow(eventId: String) = eventNetworkDataSource.getParticipantFlow(eventId)
    fun getParticipants(eventId: String)  = eventNetworkDataSource.getParticipants(eventId)
}