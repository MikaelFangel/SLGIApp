package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

interface EventDateSourceApi {
    val events: Flow<List<Event>>
}