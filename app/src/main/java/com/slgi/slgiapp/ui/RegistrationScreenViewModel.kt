package com.slgi.slgiapp.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationScreenState())
    val uiState = _uiState.asStateFlow()

    fun onFirstNameChange(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                firstname = name
            )
        }
    }

    fun onLastNameChange(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                lastname = name
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun onPasswordRepChange(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                passwordRep = password
            )
        }
    }

    fun onTermsChange() {
        _uiState.update { currentState ->
            currentState.copy(
                terms = currentState.terms.not()
            )
        }
    }
}