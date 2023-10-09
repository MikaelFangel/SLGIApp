package com.slgi.slgiapp.ui

data class RegistrationScreenState(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRep: String = "",
    val terms: Boolean = false,
)
