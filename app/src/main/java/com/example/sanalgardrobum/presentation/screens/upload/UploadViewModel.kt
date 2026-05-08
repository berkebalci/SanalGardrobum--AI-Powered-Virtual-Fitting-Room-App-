package com.example.sanalgardrobum.presentation.screens.upload

import android.util.Log
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

private const val TAG = "UploadViewModel"

data class UploadUiState(
    val userPhotoUri: String? = null,
    val clothingPhotoUri: String? = null,
    val height: String = "",
    val weight: String = "",
    val selectedSize: String = "M",
    val isAnalyzing: Boolean = false
) {
    /** Her iki fotoğraf da yüklendiyse buton aktif olur */
    val canAnalyze: Boolean get() = userPhotoUri != null && clothingPhotoUri != null
}

sealed interface UploadNavigationEvent {
    /**
     * BodyAnalysis ekranına geçerken seçilen fotoğraf URI'lerini taşır.
     * BodyAnalysisViewModel bu URI'leri kullanarak API çağrısı yapacak.
     */
    data class NavigateToBodyAnalysis(
        val userPhotoUri: String,
        val clothingPhotoUri: String
    ) : UploadNavigationEvent
}

@HiltViewModel
class UploadViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<UploadNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    // ─── Kullanıcı fotoğrafı ───────────────────────────────────────────────

    fun onUserPhotoSelected(uri: String) {
        Log.d(TAG, "Kullanıcı fotoğrafı seçildi: $uri")
        _uiState.update { it.copy(userPhotoUri = uri) }
    }

    fun onUserPhotoDeleted() {
        Log.d(TAG, "Kullanıcı fotoğrafı silindi")
        _uiState.update { it.copy(userPhotoUri = null) }
    }

    // ─── Kıyafet fotoğrafı ────────────────────────────────────────────────

    fun onClothingPhotoSelected(uri: String) {
        Log.d(TAG, "Kıyafet fotoğrafı seçildi: $uri")
        _uiState.update { it.copy(clothingPhotoUri = uri) }
    }

    fun onClothingPhotoDeleted() {
        Log.d(TAG, "Kıyafet fotoğrafı silindi")
        _uiState.update { it.copy(clothingPhotoUri = null) }
    }

    // ─── Ölçüler ──────────────────────────────────────────────────────────

    fun onHeightChanged(value: String) {
        _uiState.update { it.copy(height = value) }
    }

    fun onWeightChanged(value: String) {
        _uiState.update { it.copy(weight = value) }
    }

    fun onSizeSelected(size: String) {
        _uiState.update { it.copy(selectedSize = size) }
    }

    // ─── Analiz ───────────────────────────────────────────────────────────

    fun onAnalyzeClicked() {
        val state = _uiState.value
        if (!state.canAnalyze) return

        viewModelScope.launch {
            Log.d(TAG, "=== ANALİZ BAŞLATILIYOR ===")
            Log.d(TAG, "Kullanıcı Fotoğrafı URI: ${state.userPhotoUri}")
            Log.d(TAG, "Kıyafet Fotoğrafı URI: ${state.clothingPhotoUri}")
            Log.d(TAG, "Ölçüler → Boy: ${state.height}, Kilo: ${state.weight}, Beden: ${state.selectedSize}")

            // BodyAnalysis ekranına fotoğraf URI'lerini taşıyarak geç
            _navigationEvent.send(
                UploadNavigationEvent.NavigateToBodyAnalysis(
                    userPhotoUri = state.userPhotoUri!!,
                    clothingPhotoUri = state.clothingPhotoUri!!
                )
            )
        }
    }
}
