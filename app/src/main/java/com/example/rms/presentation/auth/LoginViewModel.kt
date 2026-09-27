package com.example.rms.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rms.data.local.SessionManager
import com.example.rms.domain.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
    }

    fun onLoginClick() {
        val current = _uiState.value
        val emailError = if (current.email.isBlank()) "Email não pode estar vazio" else null
        val passwordError = if (current.password.isBlank()) "Senha não pode estar vazia" else null

        if (emailError != null || passwordError != null) {
            _uiState.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, emailError = null, passwordError = null) }
            val result = repository.login(current.email, current.password)
            if (result.isSuccess) {
                sessionManager.setAuthenticated(true)
                _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}
