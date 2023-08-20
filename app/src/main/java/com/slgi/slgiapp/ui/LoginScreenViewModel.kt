package com.slgi.slgiapp.ui

import androidx.lifecycle.ViewModel
import com.slgi.slgiapp.data.LoginRepository
import com.slgi.slgiapp.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    suspend fun login() {
        loginRepository.logIn(
            user = User(
                email = uiState.value.email,
                password = uiState.value.password
            )
        )
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
}