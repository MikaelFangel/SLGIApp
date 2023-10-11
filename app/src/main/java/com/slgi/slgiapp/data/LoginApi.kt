package com.slgi.slgiapp.data

interface LoginApi {

    suspend fun logIn(user: User)
    suspend fun isAdmin(): Boolean
}