package com.sunpra.memories.ui.screen.login_screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunpra.memories.data.database.AppDatabase
import com.sunpra.memories.data.json.AuthErrorBody
import com.sunpra.memories.data.json.LoginBody
import com.sunpra.memories.data.json.LoginResponse
import com.sunpra.memories.utility.AppStorage
import com.sunpra.memories.utility.Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginScreenViewModel(context: Application) : AndroidViewModel(context) {
    private val appStorage = AppStorage(context)

    private val appDatabase = AppDatabase.getInstance(context)

    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _dialogMessage = MutableSharedFlow<String?>()
    val dialogMessage = _dialogMessage.asSharedFlow()

    private val _loginSuccess = MutableSharedFlow<LoginResponse?>()
    val loginSuccess = _loginSuccess.asSharedFlow()

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
                email = uiState.value.email, password = uiState.value.password
            )
            viewModelScope.launch {
                try {
                    val response: Response<LoginResponse> = withContext(Dispatchers.IO) {
                        Provider.restService.login(loginBody)
                    }

                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        appStorage.token = loginResponse?.token
                        loginResponse?.user?.let {
                            appDatabase.getUserDao()
                                .insert(it.apply { token = loginResponse.token })
                        }
                        _loginSuccess.emit(loginResponse)
                    } else {
                        val stringError: String? = response.errorBody()?.string()
                        if (stringError != null) {
                            Log.d("TAG", "stringError: $stringError")
                            val authErrorBody: AuthErrorBody? = Gson().fromJson<AuthErrorBody>(
                                stringError,
                                object : TypeToken<AuthErrorBody>() {}.type,
                            )
                            if (authErrorBody?.message != null) {
                                _dialogMessage.emit(authErrorBody.message)
                            }
                            if (authErrorBody?.email != null) {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        emailError = authErrorBody.email.joinToString(" ")
                                    )
                                }
                            }
                        }
                        //{"message":"Invalid credentials provided."}
                    }
                } catch (e: Exception) {
                    _dialogMessage.emit("Something went wrong, please try again later.")
                }
            }
        }
    }

    fun onDismissClicked() {
        viewModelScope.launch {
            _dialogMessage.emit(null)
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

//Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")