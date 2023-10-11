package com.slgi.slgiapp.data

import com.google.firebase.firestore.FirebaseFirestore

class RequestDataSource : RequestDataSourceApi {
    override suspend fun requestAccess(request: Request) {
        FirebaseFirestore.getInstance().collection(REQUEST_COLLECTION).add(request)
    }

    companion object {
        private const val REQUEST_COLLECTION = "Requests"
    }
}