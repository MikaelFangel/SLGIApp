package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

class EventRepository(
    private val eventNetworkDataSource: EventDateSourceApi
) {
    val events: Flow<List<Event>>
        get() = eventNetworkDataSource.events
}