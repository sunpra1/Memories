package com.sunpra.memories.ui.screen.memories_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunpra.memories.data.RestService
import com.sunpra.memories.data.json.Memory
import com.sunpra.memories.utility.Provider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoriesScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MemoriesScreenUiState())
    val uiState = _uiState.asStateFlow()
    init {
        getMemories()
    }
    private fun getMemories() {
        viewModelScope.launch {
            try {
                val restService: RestService = Provider.restService
                val response = restService.getMemories()
                if(response.isSuccessful){
                    _uiState.update { currentState ->
                        currentState.copy(memories = response.body())
                    }
                }else{

                }
            } catch (e: Exception) {

            }
        }
    }
}

data class MemoriesScreenUiState(
    val isLoading: Boolean = false,
    val memories: List<Memory>? = null
)