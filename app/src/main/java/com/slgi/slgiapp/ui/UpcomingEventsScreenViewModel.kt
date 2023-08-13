package com.slgi.slgiapp.ui

import com.slgi.slgiapp.data.EventRepository

class UpcomingEventsScreenViewModel(
    private val eventRepository: EventRepository
) {
    val events = eventRepository.events

    fun toggleParticipation(id: String) {
        suspend { eventRepository.toggleParticipation(id) }
    }
}