package com.slgi.slgiapp.data

class RequestRepository(private val requestDataSource: RequestDataSourceApi) : RequestDataSourceApi {
    override suspend fun requestAccess(request: Request) = requestDataSource.requestAccess(request)
}