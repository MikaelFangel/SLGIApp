package com.slgi.slgiapp.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RequestDataSource : RequestDataSourceApi {
    override suspend fun requestAccess(request: Request) {
        Firebase.firestore.collection(REQUEST_COLLECTION).add(request)
    }

    companion object {
        private const val REQUEST_COLLECTION = "Requests"
    }
}