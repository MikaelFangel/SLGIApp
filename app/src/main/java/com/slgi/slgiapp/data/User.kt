package com.slgi.slgiapp.data

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val userId: String = "",
)