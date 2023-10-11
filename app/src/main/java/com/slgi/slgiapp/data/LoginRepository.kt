package com.slgi.slgiapp.data

class LoginRepository(private val loginFirebaseDataSource: LoginApi) : LoginApi {
    override suspend fun logIn(user: User) {
        loginFirebaseDataSource.logIn(user)
    }

    override suspend fun isAdmin(): Boolean {
        return loginFirebaseDataSource.isAdmin()
    }
}