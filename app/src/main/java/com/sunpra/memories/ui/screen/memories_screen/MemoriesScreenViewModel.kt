package com.sunpra.memories.ui.screen.memories_screen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunpra.memories.data.RestService
import com.sunpra.memories.data.json.Memory
import com.sunpra.memories.utility.AppStorage
import com.sunpra.memories.utility.Provider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoriesScreenViewModel(context: Application) : AndroidViewModel(context) {

    private val appStorage = AppStorage(context)

    private val _uiState = MutableStateFlow(MemoriesScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _dialogMessage = MutableSharedFlow<String?>()
    val dialogMessage = _dialogMessage.asSharedFlow()

    fun onDismissClicked() {
        viewModelScope.launch {
            _dialogMessage.emit(null)
        }
    }
    fun getMemories() {
        viewModelScope.launch {
            try {
                val restService: RestService = Provider.restService
                val response = restService.getMemories("Bearer ${appStorage.token}")
                if(response.isSuccessful){
                    _uiState.update { currentState ->
                        currentState.copy(memories = response.body() ?: listOf())
                    }
                }else{
                    _dialogMessage.emit("Failed getting memories.")
                }
            } catch (e: Exception) {
                _dialogMessage.emit("Something went wrong, please try again later.")
            }
        }
    }
}

data class MemoriesScreenUiState(
    val isLoading: Boolean = false,
    val memories: List<Memory> = listOf()
)