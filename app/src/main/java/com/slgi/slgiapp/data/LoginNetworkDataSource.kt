package com.slgi.slgiapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginNetworkDataSource : LoginApi {
    override suspend fun logIn(user: User) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email, user.password).await()
    }

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun isAdmin(): Boolean {
        return FirebaseFirestore.getInstance().collection("Admins").document(
            FirebaseAuth.getInstance().currentUser!!.uid
        ).get().await().exists()
    }

    override suspend fun resetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }

    override fun addUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
    }
}