package com.slgi.slgiapp.ui

import androidx.lifecycle.ViewModel
import com.slgi.slgiapp.data.Request
import com.slgi.slgiapp.data.RequestDataSourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationScreenViewModel(private val requestRepository: RequestDataSourceApi) :
    ViewModel() {
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

    // TODO implement checks properly
    suspend fun requestAccess(): Boolean {
        if (preChecks()) {
            val req = Request(
                "",
                uiState.value.firstname,
                uiState.value.lastname,
                uiState.value.email,
                uiState.value.password
            )

            requestRepository.requestAccess(req)
        }
        return preChecks()
    }

    private fun preChecks(): Boolean {
        // Check the passwords match
        if (uiState.value.password != uiState.value.passwordRep || uiState.value.password.isEmpty())
            return false
        // Check that the user has accepted terms and cons
        if (uiState.value.terms.not())
            return false
        return true
    }
}