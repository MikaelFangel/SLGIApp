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
    @Throws(Exception::class)
    suspend fun requestAccess() {
        if (preChecks()) {
            val req = Request(
                "",
                uiState.value.firstname,
                uiState.value.lastname,
                uiState.value.email,
                uiState.value.password
            )

            requestRepository.requestAccess(req)
        } else {
            throw Exception("Din anmodning fejlede. Prøv igen!")
        }
    }

    private fun preChecks(): Boolean {
        // Check that there is text in the name fields
        if (uiState.value.firstname.isEmpty() || uiState.value.lastname.isEmpty())
            return false
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if(uiState.value.email.matches(emailRegex.toRegex()).not())
            return false
        // Check the passwords match
        if (uiState.value.password != uiState.value.passwordRep
            || uiState.value.password.isEmpty()
            || uiState.value.password.length < 8)
            return false
        // Check that the user has accepted terms and cons
        if (uiState.value.terms.not())
            return false
        return true
    }
}