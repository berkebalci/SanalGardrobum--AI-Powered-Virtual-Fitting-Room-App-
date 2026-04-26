package com.example.sanalgardrobum.presentation.screens.tryon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.GridView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sanalgardrobum.presentation.screens.common.FilterCategory
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ClothItem(
    val id: String,
    val name: String,
    val category: String,
    val color: String,
    val season: String
)

data class TryOnUiState(
    val selectedCategory: String = "all",
    val wardrobeItems: List<ClothItem> = emptyList(),
    val selectedClothIds: Set<String> = emptySet(),
    val isSimulating: Boolean = false,
    val categories: List<FilterCategory> = defaultCategories
) {
    val filteredItems: List<ClothItem>
        get() = if (selectedCategory == "all") wardrobeItems
        else wardrobeItems.filter { it.category == selectedCategory }

    val selectedCount: Int get() = selectedClothIds.size
    val canSimulate: Boolean get() = selectedClothIds.isNotEmpty()

    companion object {
        private val defaultCategories = listOf(
            FilterCategory("all", "Tümü", Icons.Outlined.GridView),
            FilterCategory("top", "Üst", Icons.Outlined.Checkroom),
            FilterCategory("bottom", "Alt", Icons.Outlined.Checkroom),
            FilterCategory("dress", "Elbise", Icons.Outlined.Checkroom),
            FilterCategory("outer", "Dış Giyim", Icons.Outlined.Checkroom)
        )
    }
}

sealed interface TryOnNavigationEvent {
    data object NavigateToSimulationResult : TryOnNavigationEvent
}

class TryOnViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TryOnUiState())
    val uiState: StateFlow<TryOnUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TryOnNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadWardrobeItems()
    }

    private fun loadWardrobeItems() {
        // TODO: Repository'den gerçek verileri çek
        val mockItems = listOf(
            ClothItem("1", "Beyaz Gömlek", "top", "Beyaz", "Tüm Mevsim"),
            ClothItem("2", "Siyah Pantolon", "bottom", "Siyah", "Tüm Mevsim"),
            ClothItem("3", "Mavi Kot Ceket", "outer", "Mavi", "İlkbahar"),
            ClothItem("4", "Çiçekli Elbise", "dress", "Çok Renkli", "Yaz"),
            ClothItem("5", "Gri Kazak", "top", "Gri", "Kış"),
            ClothItem("6", "Kahverengi Etek", "bottom", "Kahverengi", "Sonbahar")
        )
        _uiState.update { it.copy(wardrobeItems = mockItems) }
    }

    fun onCategorySelected(categoryId: String) {
        _uiState.update { it.copy(selectedCategory = categoryId) }
    }

    fun onClothToggled(clothId: String) {
        _uiState.update { state ->
            val newSelection = state.selectedClothIds.toMutableSet().apply {
                if (contains(clothId)) remove(clothId) else add(clothId)
            }
            state.copy(selectedClothIds = newSelection)
        }
    }

    fun onSimulateClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSimulating = true) }
            // TODO: API call → FastAPI /generate-tryon
            _navigationEvent.send(TryOnNavigationEvent.NavigateToSimulationResult)
        }
    }
}
