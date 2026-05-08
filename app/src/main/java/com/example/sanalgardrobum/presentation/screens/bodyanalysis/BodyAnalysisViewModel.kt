package com.example.sanalgardrobum.presentation.screens.bodyanalysis

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sanalgardrobum.data.repository.TryOnRepository
import com.example.sanalgardrobum.data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "BodyAnalysisVM"

data class BodyAnalysisUiState(
    val progress: Float = 0f,
    val currentStep: Int = 0,
    val steps: List<String> = listOf(
        "Fotoğraf İşleniyor",
        "Vücut Segmentasyonu",
        "Poz Analizi",
        "Kıyafet Eşleştirme",
        "Tamamlanıyor"
    ),
    val errorMessage: String? = null,
    val resultImagePath: String? = null
)

sealed interface BodyAnalysisNavigationEvent {
    data class NavigateToSimulationResult(val imagePath: String) : BodyAnalysisNavigationEvent
}

@HiltViewModel
class BodyAnalysisViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tryOnRepository: TryOnRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BodyAnalysisUiState())
    val uiState: StateFlow<BodyAnalysisUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<BodyAnalysisNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    // Navigation argument'lerinden URI'leri al
    private val personImageUri: String = savedStateHandle.get<String>("personImageUri") ?: ""
    private val garmentImageUri: String = savedStateHandle.get<String>("garmentImageUri") ?: ""

    init {
        Log.d(TAG, "BodyAnalysisViewModel başlatıldı")
        Log.d(TAG, "Person URI: $personImageUri")
        Log.d(TAG, "Garment URI: $garmentImageUri")

        if (personImageUri.isNotEmpty() && garmentImageUri.isNotEmpty()) {
            startRealAnalysis()
        } else {
            Log.e(TAG, "Fotoğraf URI'leri eksik! Analiz başlatılamıyor.")
            _uiState.update { it.copy(errorMessage = "Fotoğraf bilgileri eksik. Lütfen geri dönüp tekrar deneyin.") }
        }
    }

    /**
     * Gerçek API çağrısı ile try-on analizi yapar.
     * Progress bar'ı API cevabı gelene kadar yavaşça ilerletir,
     * cevap gelince %100'e tamamlar.
     */
    private fun startRealAnalysis() {
        viewModelScope.launch {
            Log.d(TAG, "=== GERÇEK API ÇAĞRISI BAŞLATILIYOR ===")

            // Progress bar'ı yavaşça ilerlet (API cevabını beklerken)
            val progressJob = launch {
                while (_uiState.value.progress < 0.90f) {
                    delay(200)
                    _uiState.update { state ->
                        val newProgress = (state.progress + 0.008f).coerceAtMost(0.90f)
                        state.copy(
                            progress = newProgress,
                            currentStep = ((newProgress * 5).toInt()).coerceAtMost(4)
                        )
                    }
                }
            }

            // Repository üzerinden gerçek API çağrısı
            tryOnRepository.generateTryOn(personImageUri, garmentImageUri).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(TAG, "Repository: Loading durumu")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "=== API BAŞARIYLA CEVAP VERDİ ===")
                        Log.d(TAG, "Sonuç dosya yolu: ${resource.data}")

                        progressJob.cancel()

                        // %100'e tamamla
                        _uiState.update {
                            it.copy(
                                progress = 1f,
                                currentStep = 4,
                                resultImagePath = resource.data
                            )
                        }

                        delay(800) // Kullanıcının %100'ü görmesi için kısa bekleme
                        _navigationEvent.send(
                            BodyAnalysisNavigationEvent.NavigateToSimulationResult(resource.data)
                        )
                    }

                    is Resource.Error -> {
                        Log.e(TAG, "=== API HATASI ===")
                        Log.e(TAG, "Hata: ${resource.message}")
                        resource.exception?.let { Log.e(TAG, "Exception:", it) }

                        progressJob.cancel()

                        _uiState.update {
                            it.copy(
                                progress = 0f,
                                currentStep = 0,
                                errorMessage = resource.message
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Hata durumunda tekrar deneme
     */
    fun onRetryClicked() {
        _uiState.update { it.copy(errorMessage = null, progress = 0f, currentStep = 0) }
        startRealAnalysis()
    }
}
