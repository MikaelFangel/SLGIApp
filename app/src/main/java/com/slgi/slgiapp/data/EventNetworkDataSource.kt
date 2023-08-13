package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class EventNetworkDataSource {
    val events: Flow<List<Event>>
        get() = Firebase.firestore
            .collection(EVENT_COLLECTION)
            .orderBy(DATE_FIELD)
            .whereGreaterThanOrEqualTo(DATE_FIELD, Timestamp.now())
            .dataObjects()

    companion object {
        private const val EVENT_COLLECTION = "Events"
        private const val DATE_FIELD = "date"
        private const val TIME_FIELD = "time"
    }
}