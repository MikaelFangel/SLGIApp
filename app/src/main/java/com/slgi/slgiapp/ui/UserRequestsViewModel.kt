package com.slgi.slgiapp.ui

import com.slgi.slgiapp.data.LoginRepository
import com.slgi.slgiapp.data.Request
import com.slgi.slgiapp.data.RequestRepository

class UserRequestsViewModel(
    private val requestReposiory: RequestRepository,
    private val loginRepository: LoginRepository
) {
    val requests = requestReposiory.getPendingRequests()

    suspend fun approveUser(request: Request){
        loginRepository.addUser(request.email,request.password)
        this.deleteUserRequest(request)
    }

    suspend fun deleteUserRequest(request: Request) {
        requestReposiory.deleteRequest(request)
    }
}