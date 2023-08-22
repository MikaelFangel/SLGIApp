package com.slgi.slgiapp.data

interface LoginApi {
    suspend fun logIn(user: User)
}