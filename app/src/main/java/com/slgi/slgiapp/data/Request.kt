package com.slgi.slgiapp.data

import com.google.firebase.firestore.DocumentId

data class Request(
    @DocumentId val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
)
