package com.slgi.slgiapp.ui

import androidx.lifecycle.ViewModel
import com.slgi.slgiapp.data.LoginRepository
import com.slgi.slgiapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

        isAdmin()
    }

    private fun isAdmin() {
        CoroutineScope(Dispatchers.Main).launch {
            _uiState.update { c ->
                c.copy(
                    isAdmin = loginRepository.isAdmin()
                )
            }
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
}