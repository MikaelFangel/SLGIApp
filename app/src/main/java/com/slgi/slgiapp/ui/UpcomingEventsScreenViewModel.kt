package com.slgi.slgiapp.ui

import com.slgi.slgiapp.data.EventRepository
import com.slgi.slgiapp.data.User
import kotlinx.coroutines.flow.Flow

class UpcomingEventsScreenViewModel(
    private val eventRepository: EventRepository
) {
    val events = eventRepository.events

    suspend fun toggleParticipation(id: String) = eventRepository.toggleParticipation(id)
    fun getParticipantFlow(eventId: String): Flow<List<User>>? = eventRepository.getParticipantFlow(eventId)
}