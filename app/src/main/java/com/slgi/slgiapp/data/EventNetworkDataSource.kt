package com.slgi.slgiapp.data

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class EventNetworkDataSource : EventDateSourceApi {
    private val eventCollection: CollectionReference
        get() = Firebase.firestore
            .collection(EVENT_COLLECTION)

    override val events: Flow<List<Event>>
        get() = eventCollection
            .orderBy(DATE_FIELD)
            .whereGreaterThanOrEqualTo(DATE_FIELD, Timestamp.now())
            .dataObjects()

    override suspend fun toggleParticipation(eventId: String) {
        val userId = Firebase.auth.uid ?: return

        if (!isParticipating(eventId)) {
            participantCollection(eventId).add(User(userId = userId)).await()
        } else {
            participantCollection(eventId).whereEqualTo(USERID_FIELD, userId)
                .get().await().documents.forEach { d ->
                    participantCollection(eventId).document(d.id).delete()
                }
        }
    }

    private suspend fun isParticipating(eventId: String): Boolean {
        val userId = Firebase.auth.uid ?: return false

        // Check if user is registered or not
        return participantCollection(eventId)
            .whereEqualTo(USERID_FIELD, userId)
            .get().await().documents.isNotEmpty()
    }

    override fun getParticipantFlow(eventId: String): Flow<List<User>>? {
        val userId = Firebase.auth.uid ?: return null

        return eventCollection
            .document(eventId)
            .collection(PARTICIPATION_COLLECTION)
            .whereEqualTo(USERID_FIELD, userId)
            .dataObjects()
    }

    override fun getParticipants(eventId: String): Flow<List<User>> {
        return eventCollection
            .document(eventId)
            .collection(PARTICIPATION_COLLECTION)
            .dataObjects()
    }

    override fun createEvent(event: Event) {
        eventCollection.add(event)
    }

    private fun participantCollection(eventId: String): CollectionReference =
        eventCollection.document(eventId).collection(PARTICIPATION_COLLECTION)

    companion object {
        private const val EVENT_COLLECTION = "Events"
        private const val PARTICIPATION_COLLECTION = "Participants"
        private const val DATE_FIELD = "dateAndTime"
        private const val USERID_FIELD = "userId"
    }
}