package com.sunpra.memories.ui.screen.add_memory_screen

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sunpra.memories.data.json.AddMemoryBody
import com.sunpra.memories.utility.AppStorage
import com.sunpra.memories.utility.Provider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddMemoryScreenViewModel(context: Application) : AndroidViewModel(context) {

    private val contentResolver: ContentResolver =
        context.contentResolver

    private val appStorage: AppStorage =
        AppStorage(context)

    private val _uiState = MutableStateFlow(AddMemoryScreenUiState())
    val uiState = _uiState.asStateFlow()


    private val _dialogMessage = MutableSharedFlow<String?>()
    val dialogMessage = _dialogMessage.asSharedFlow()


    private val _onAddMemorySuccess = MutableSharedFlow<Boolean>()
    val onAddMemorySuccess = _onAddMemorySuccess.asSharedFlow()

    fun onDismissClicked() {
        viewModelScope.launch {
            _dialogMessage.emit(null)
        }
    }
    fun onImageSelected(imageUri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(image = imageUri)
        }
    }

    fun onTitleChanged(value: String){
        _uiState.update { currentState ->
            currentState.copy(title = value)
        }
    }

    fun onDescriptionChanged(value: String){
        _uiState.update { currentState ->
            currentState.copy(description = value)
        }
    }

    fun onAddMemoryClicked() {
        viewModelScope.launch {
            if (validate()) {
                try {
                    val token: String? = appStorage.token
                    val addMemoryBody = AddMemoryBody(
                        title = _uiState.value.title,
                        description = _uiState.value.description,
                        image = checkNotNull(contentResolver.openInputStream(checkNotNull(_uiState.value.image)))
                    )
                    val response = Provider.restService.addMemory(
                        token = "Bearer $token",
                        body = addMemoryBody.asMultipartBody()
                    )
                    if(response.isSuccessful){
                        _onAddMemorySuccess.emit(true)
                    }else{
                        throw Exception("Failed to add memory. Please, try again later.")
                    }
                } catch (e: Exception) {
                    viewModelScope.launch {
                        _dialogMessage.emit(e.message)
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        var titleError: String? = null
        var descriptionError: String? = null
        var imageError: String? = null

        if (_uiState.value.title.isEmpty()) {
            isValid = false
            titleError = "Title is required."
        }

        if (_uiState.value.description.isEmpty()) {
            isValid = false
            descriptionError = "Description is required."
        }

        if (_uiState.value.image == null) {
            isValid = false
            imageError = "Image is required."
        }

        _uiState.update { currentState ->
            currentState.copy(
                imageError = imageError,
                titleError = titleError,
                descriptionError = descriptionError
            )
        }

        return isValid
    }
}

data class AddMemoryScreenUiState(
    val title: String = "",
    val description: String = "",
    val image: Uri? = null,
    val titleError: String? = null,
    val descriptionError: String? = null,
    val imageError: String? = null
)