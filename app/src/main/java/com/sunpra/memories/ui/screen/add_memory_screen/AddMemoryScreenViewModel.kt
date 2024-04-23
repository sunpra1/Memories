package com.sunpra.memories.ui.screen.add_memory_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddMemoryScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AddMemoryScreenUiState())
    val uiState = _uiState.asStateFlow()
}

data class AddMemoryScreenUiState(
    val title: String = "",
    val description: String = "",
    val image: Uri? = null,
    val titleError: String? = null,
    val emailError: String? = null,
    val imageError: String? = null
)