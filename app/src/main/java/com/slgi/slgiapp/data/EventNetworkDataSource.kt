package com.slgi.slgiapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class EventNetworkDataSource {
    val events: Flow<List<Event>>
        get() = Firebase.firestore
            .collection(EVENT_COLLECTION).dataObjects()

    companion object {
        private const val EVENT_COLLECTION = "Events"
    }
}