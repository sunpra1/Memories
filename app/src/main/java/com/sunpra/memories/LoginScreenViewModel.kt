package com.sunpra.memories

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

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

    fun onLoginClicked(){
        if(validate()){

        }
    }

    fun validate() : Boolean{
        var isValid = true

        val email = uiState.value.email
        if(email.isEmpty()){
            isValid = false
            _uiState.update { currentState ->
                currentState.copy(emailError =  "Email is required.")
            }
        }else if(!Pattern.matches("^(.+)@(\\\\S+)\$", email)){
            isValid = false
            _uiState.update { currentState ->
                currentState.copy(emailError =  "Invalid email address.")
            }
        }

        return isValid
    }
}

data class LoginScreenUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val emailError: String? = null
)