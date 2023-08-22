package com.slgi.slgiapp.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LoginNetworkDataSource : LoginApi {

    override suspend fun logIn(user: User) {
        Firebase.auth.signInWithEmailAndPassword(user.email, user.password).await()
    }
}