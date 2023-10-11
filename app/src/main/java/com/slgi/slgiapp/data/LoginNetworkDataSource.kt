package com.slgi.slgiapp.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginNetworkDataSource : LoginApi {

    override suspend fun logIn(user: User) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email, user.password).await()
    }
}