package com.sunpra.memories.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunpra.memories.data.json.LoginBody
import com.sunpra.memories.data.json.LoginResponse
import com.sunpra.memories.utility.Provider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.regex.Pattern

class LoginScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        _uiState.update { currentState ->
            currentState.copy(email = value)
        }
    }

    fun onPasswordToggled() {
        _uiState.update { currentState ->
            currentState.copy(isPasswordVisible = !currentState.isPasswordVisible)
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { currentState ->
            currentState.copy(password = value)
        }
    }

    fun rememberMeToggled(value: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(rememberMe = !currentState.rememberMe)
        }
    }

    fun onLoginClicked() {
        if (validate()) {
            val loginBody = LoginBody(
                email = uiState.value.email,
                password = uiState.value.password
            )
            viewModelScope.launch {
                val response: Response<LoginResponse> =
                    Provider.restService.login(loginBody)
                if(response.isSuccessful){
                    Log.d("LOGIN SUCCESS", response.toString())
                }else{
                    Log.d("LOGIN FAILED", response.errorBody()?.string() ?: "UNKNOWN")
                }
            }
        }
    }

    fun validate(): Boolean {
        var isValid = true

        val email = uiState.value.email
        if (email.isEmpty()) {
            isValid = false
            _uiState.update { currentState ->
                currentState.copy(emailError = "Email is required.")
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