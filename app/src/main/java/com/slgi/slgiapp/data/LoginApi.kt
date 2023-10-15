package com.slgi.slgiapp.data

interface LoginApi {

    suspend fun logIn(user: User)
    suspend fun logout()
    suspend fun isAdmin(): Boolean
    suspend fun resetPassword(email: String)
    fun addUser(email: String, password: String)
    suspend fun deleteUser()
}