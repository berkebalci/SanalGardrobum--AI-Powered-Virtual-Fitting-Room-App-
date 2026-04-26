package com.example.sanalgardrobum.presentation.screens.combinations

import androidx.lifecycle.ViewModel
import com.example.sanalgardrobum.presentation.screens.common.FilterCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CombinationData(
    val id: Int,
    val name: String,
    val style: String,
    val score: Int,
    val items: List<String>,
    val season: String,
    val description: String
)

data class CombinationsUiState(
    val activeFilter: String = "all",
    val combinations: List<CombinationData> = emptyList(),
    val filters: List<FilterCategory> = defaultFilters
) {
    val filteredCombinations: List<CombinationData>
        get() = if (activeFilter == "all") combinations
        else combinations.filter { it.style == activeFilter }

    companion object {
        private val defaultFilters = listOf(
            FilterCategory("all", "Tümü"),
            FilterCategory("casual", "Günlük"),
            FilterCategory("formal", "Resmi"),
            FilterCategory("sport", "Spor"),
            FilterCategory("party", "Parti")
        )
    }
}

class CombinationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CombinationsUiState())
    val uiState: StateFlow<CombinationsUiState> = _uiState.asStateFlow()

    init {
        loadCombinations()
    }

    private fun loadCombinations() {
        // TODO: Repository'den gerçek verileri çek
        val mockCombinations = listOf(
            CombinationData(1, "Klasik İş Kombini", "formal", 96, listOf("Beyaz Gömlek", "Siyah Pantolon", "Siyah Blazer"), "Tüm Mevsim", "Profesyonel ve şık bir iş görünümü için mükemmel kombinasyon"),
            CombinationData(2, "Rahat Günlük Stil", "casual", 94, listOf("Gri Kazak", "Mavi Kot Pantolon", "Beyaz Spor Ayakkabı"), "Sonbahar", "Rahat ve şık bir günlük görünüm için ideal"),
            CombinationData(3, "Yaz Şıklığı", "casual", 92, listOf("Çiçekli Elbise", "Hasır Şapka", "Sandalet"), "Yaz", "Yaz günleri için ferah ve şık bir kombinasyon"),
            CombinationData(4, "Spor Şık", "sport", 90, listOf("Siyah Sweatshirt", "Gri Eşofman", "Spor Ayakkabı"), "Tüm Mevsim", "Spor ve rahat bir görünüm için mükemmel"),
            CombinationData(5, "Akşam Şıklığı", "party", 95, listOf("Siyah Elbise", "Topuklu Ayakkabı", "Clutch Çanta"), "Tüm Mevsim", "Özel davetler için zarif ve şık bir kombinasyon"),
            CombinationData(6, "Kış Sıcaklığı", "casual", 93, listOf("Kahverengi Kazak", "Siyah Pantolon", "Bot"), "Kış", "Soğuk havalarda sıcak ve şık kalmanın yolu")
        )
        _uiState.update { it.copy(combinations = mockCombinations) }
    }

    fun onFilterSelected(filterId: String) {
        _uiState.update { it.copy(activeFilter = filterId) }
    }
}
