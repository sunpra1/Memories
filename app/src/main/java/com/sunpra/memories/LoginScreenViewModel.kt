package com.sunpra.memories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(value: String){
        _uiState.update { currentState ->
            currentState.copy(email = value)
        }
    }

    fun onPasswordToggled(){
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }

    fun onPasswordChanged(value: String){
        _uiState.update { currentState ->
            currentState.copy(password = value)
        }
    }

    fun rememberMeToggled(value: Boolean){
        _uiState.update { currentState ->
            currentState.copy(rememberMe = !currentState.rememberMe)
        }
    }
}

data class LoginScreenUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isPasswordVisible: Boolean = false
)