package com.sunpra.memories.ui.screen.memories_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sunpra.memories.data.RestService
import com.sunpra.memories.data.database.AppDatabase
import com.sunpra.memories.data.json.Memory
import com.sunpra.memories.data.json.User
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

    private val appDatabase = AppDatabase.getInstance(context)

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
            val token = appStorage.token
            val user: User = appDatabase.getUserDao().getUserHavingToken(checkNotNull(token))
            try {
                val restService: RestService = Provider.restService
                val response = restService.getMemories("Bearer ${appStorage.token}")
                if (response.isSuccessful) {
                    val memories: List<Memory>? = response.body()
                    memories?.let {
                        val dbMemories = memories.map { it.apply { userId = user.id } }
                        appDatabase.getMemoryDao().insertAll(
                            *dbMemories.toTypedArray()
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(memories = memories ?: listOf())
                    }

                } else {
                    throw Exception("Failed getting memories")
                }
            } catch (e: Exception) {
                val userWithMemories = appDatabase.getUserDao().getUserWithMemoriesHavingId(user.id)
                _uiState.update { currentState ->
                    currentState.copy(memories = userWithMemories.memories)
                }
            }
        }
    }
}

data class MemoriesScreenUiState(
    val isLoading: Boolean = false,
    val memories: List<Memory> = listOf()
)