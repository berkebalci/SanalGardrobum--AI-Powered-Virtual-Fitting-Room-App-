package com.example.sanalgardrobum.presentation.screens.combinationdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class CombinationDetailData(
    val id: Int,
    val name: String,
    val style: String,
    val score: Int,
    val items: List<String>,
    val season: String,
    val description: String
)

data class CombinationDetailUiState(
    val combo: CombinationDetailData = CombinationDetailData(0, "", "", 0, emptyList(), "", ""),
    val isSaved: Boolean = false,
    val activeTab: String = "details"
) {
    val styleLabel: String
        get() = styleLabels[combo.style] ?: ""

    companion object {
        val styleLabels = mapOf(
            "formal" to "Resmi",
            "casual" to "Günlük",
            "sport" to "Spor",
            "party" to "Parti"
        )
    }
}
@HiltViewModel
class CombinationDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val comboId: Int = savedStateHandle["comboId"] ?: 0

    private val _uiState = MutableStateFlow(CombinationDetailUiState())
    val uiState: StateFlow<CombinationDetailUiState> = _uiState.asStateFlow()

    init {
        loadCombinationDetail(comboId)
    }

    private fun loadCombinationDetail(id: Int) {
        // TODO: Repository'den gerçek veriyi çek
        val mockCombo = CombinationDetailData(
            id = id,
            name = "Klasik İş Kombini",
            style = "formal",
            score = 96,
            items = listOf("Beyaz Gömlek", "Siyah Pantolon", "Siyah Blazer"),
            season = "Tüm Mevsim",
            description = "Profesyonel ve şık bir iş görünümü için mükemmel kombinasyon"
        )
        _uiState.update { it.copy(combo = mockCombo) }
    }

    fun onTabSelected(tabId: String) {
        _uiState.update { it.copy(activeTab = tabId) }
    }

    fun onSaveToggled() {
        _uiState.update { it.copy(isSaved = !it.isSaved) }
    }
}
