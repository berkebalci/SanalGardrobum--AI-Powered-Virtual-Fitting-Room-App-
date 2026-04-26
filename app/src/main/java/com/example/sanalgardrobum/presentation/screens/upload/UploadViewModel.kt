package com.example.sanalgardrobum.presentation.screens.upload

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UploadUiState(
    val uploadedImageUri: String? = null,
    val height: String = "",
    val weight: String = "",
    val selectedSize: String = "M",
    val isAnalyzing: Boolean = false
)

sealed interface UploadNavigationEvent {
    data object NavigateToBodyAnalysis : UploadNavigationEvent
}

@HiltViewModel
class UploadViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<UploadNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onPhotoSelected() {
        // TODO: Galeri/kamera intent → Firebase Storage upload → URL al
        _uiState.update { it.copy(uploadedImageUri = "mock_uploaded") }
    }

    fun onPhotoDeleted() {
        _uiState.update { it.copy(uploadedImageUri = null) }
    }

    fun onHeightChanged(value: String) {
        _uiState.update { it.copy(height = value) }
    }

    fun onWeightChanged(value: String) {
        _uiState.update { it.copy(weight = value) }
    }

    fun onSizeSelected(size: String) {
        _uiState.update { it.copy(selectedSize = size) }
    }

    fun onAnalyzeClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isAnalyzing = true) }
            // TODO: Firebase'e yükleme + API call burada yapılacak
            _navigationEvent.send(UploadNavigationEvent.NavigateToBodyAnalysis)
        }
    }
}
