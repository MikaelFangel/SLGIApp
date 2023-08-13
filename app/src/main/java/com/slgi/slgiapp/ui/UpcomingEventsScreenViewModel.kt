package com.slgi.slgiapp.ui

import com.slgi.slgiapp.data.EventRepository

class UpcomingEventsScreenViewModel(
    private val eventRepository: EventRepository
) {
    val events = eventRepository.events

    suspend fun toggleParticipation(id: String) = eventRepository.toggleParticipation(id)
    suspend fun isParticipating(id: String) = eventRepository.isParticipating(id)
}