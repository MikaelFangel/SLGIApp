package com.slgi.slgiapp.data

import kotlinx.coroutines.flow.Flow

class RequestRepository(private val requestDataSource: RequestDataSourceApi) : RequestDataSourceApi {
    override suspend fun requestAccess(request: Request) = requestDataSource.requestAccess(request)

    override fun getPendingRequests(): Flow<List<Request>> {
        return requestDataSource.getPendingRequests()
    }

    override suspend fun deleteRequest(request: Request) {
        requestDataSource.deleteRequest(request)
    }
}