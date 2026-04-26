package com.example.sanalgardrobum.presentation.screens.bodyanalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BodyAnalysisUiState(
    val progress: Float = 0f,
    val currentStep: Int = 0,
    val steps: List<String> = listOf(
        "Fotoğraf İşleniyor",
        "Vücut Segmentasyonu",
        "Poz Analizi",
        "Oran Hesaplama",
        "Tamamlanıyor"
    )
)

sealed interface BodyAnalysisNavigationEvent {
    data object NavigateToTryOn : BodyAnalysisNavigationEvent
}
@HiltViewModel
class BodyAnalysisViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BodyAnalysisUiState())
    val uiState: StateFlow<BodyAnalysisUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<BodyAnalysisNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        startAnalysis()
    }

    private fun startAnalysis() {
        viewModelScope.launch {
            while (_uiState.value.progress < 1f) {
                delay(100)
                _uiState.update { state ->
                    val newProgress = (state.progress + 0.02f).coerceAtMost(1f)
                    state.copy(
                        progress = newProgress,
                        currentStep = ((newProgress * 5).toInt()).coerceAtMost(4)
                    )
                }
            }
            delay(500)
            _navigationEvent.send(BodyAnalysisNavigationEvent.NavigateToTryOn)
        }
    }
}
