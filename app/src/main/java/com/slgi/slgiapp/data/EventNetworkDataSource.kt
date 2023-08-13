package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class EventNetworkDataSource : EventDateSourceApi {
    override val events: Flow<List<Event>>
        get() = Firebase.firestore
            .collection(EVENT_COLLECTION)
            .orderBy(DATE_FIELD)
            .whereGreaterThanOrEqualTo(DATE_FIELD, Timestamp.now())
            .dataObjects()

    override suspend fun toggleParticipation(id: String) {
        val userId = Firebase.auth.uid?: return

        val participationCollection = Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(id)
            .collection(PARTICIPATION_COLLECTION)

        if (!isParticipating(id)) {
            participationCollection.add(User(userId = userId)).await()
        } else {
            participationCollection.whereEqualTo(USERID_FIELD, userId)
                .get().await().documents.forEach { d ->
                    participationCollection.document(d.id).delete()
                }
        }
    }

    override suspend fun isParticipating(id: String): Boolean {
        val userId = Firebase.auth.uid?: return false

        val participationCollection = Firebase.firestore
            .collection(EVENT_COLLECTION)
            .document(id)
            .collection(PARTICIPATION_COLLECTION)

        // Check if user is registered or not
        return participationCollection
            .whereEqualTo(USERID_FIELD, userId)
            .get().await().documents.isNotEmpty()
    }

    companion object {
        private const val EVENT_COLLECTION = "Events"
        private const val PARTICIPATION_COLLECTION = "Participants"
        private const val DATE_FIELD = "dateAndTime"
        private const val USERID_FIELD = "userId"
    }
}