package com.slgi.slgiapp.data

class LoginRepository(private val loginFirebaseDataSource: LoginApi) : LoginApi {
    override suspend fun logIn(user: User) {
        loginFirebaseDataSource.logIn(user)
    }

    override suspend fun logout() {
        loginFirebaseDataSource.logout()
    }

    override suspend fun isAdmin(): Boolean {
        return loginFirebaseDataSource.isAdmin()
    }

    override suspend fun resetPassword(email: String) {
        loginFirebaseDataSource.resetPassword(email)
    }

    override suspend fun deleteUser() {
        loginFirebaseDataSource.deleteUser()
    }

    override fun addUser(email: String, password: String) {
        loginFirebaseDataSource.addUser(email,password)
    }
}