package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class EventNetworkDataSource: EventDateSourceApi {
    override val events: Flow<List<Event>>
        get() = Firebase.firestore
            .collection(EVENT_COLLECTION)
            .orderBy(DATE_FIELD)
            .whereGreaterThanOrEqualTo(DATE_FIELD, Timestamp.now())
            .dataObjects()

    override suspend fun toggleParticipation(id: String) {
        Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(id)
            .collection(PARTICIPATION_COLLECTION)
            .add(User(userId = Firebase.auth.uid.toString())).await()
    }

    companion object {
        private const val EVENT_COLLECTION = "Events"
        private const val PARTICIPATION_COLLECTION = "Participants"
        private const val DATE_FIELD = "dateAndTime"
    }
}