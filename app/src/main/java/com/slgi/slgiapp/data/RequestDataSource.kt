package com.slgi.slgiapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow

class RequestDataSource : RequestDataSourceApi {
    override suspend fun requestAccess(request: Request) {
        FirebaseFirestore.getInstance().collection(REQUEST_COLLECTION).add(request)
    }

    override fun getPendingRequests(): Flow<List<Request>> {
        return (FirebaseFirestore.getInstance().collection((REQUEST_COLLECTION)).dataObjects())
    }

    override suspend fun deleteRequest(request: Request) {
        (FirebaseFirestore.getInstance().collection(REQUEST_COLLECTION)).document(request.id).delete()
    }

    companion object {
        private const val REQUEST_COLLECTION = "Requests"
    }
}