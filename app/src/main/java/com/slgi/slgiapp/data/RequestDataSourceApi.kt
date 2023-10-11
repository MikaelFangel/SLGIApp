package com.slgi.slgiapp.data

interface RequestDataSourceApi {
    suspend fun requestAccess(request: Request)
}