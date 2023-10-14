package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

interface RequestDataSourceApi {
    suspend fun requestAccess(request: Request)

    fun getPendingRequests(): Flow<List<Request>>
    suspend fun deleteRequest(request: Request)
}