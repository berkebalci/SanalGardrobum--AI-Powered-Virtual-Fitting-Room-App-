package com.example.sanalgardrobum.presentation.screens.tryon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.GridView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.sanalgardrobum.data.repository.TryOnRepository
import com.example.sanalgardrobum.data.util.Resource
import com.example.sanalgardrobum.presentation.screens.utils.FilterCategory
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
    val season: String,
    val imagePath: String? = null // Gerçek kıyafet fotoğrafının yerel yolu
)

data class TryOnUiState(
    val selectedCategory: String = "all",
    val wardrobeItems: List<ClothItem> = emptyList(),
    val selectedClothIds: Set<String> = emptySet(),
    val isSimulating: Boolean = false,
    val categories: List<FilterCategory> = defaultCategories,
    // API sonucu
    val resultImagePath: String? = null,
    val errorMessage: String? = null
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
    data class NavigateToSimulationResult(val resultImagePath: String) : TryOnNavigationEvent
}

@HiltViewModel
class TryOnViewModel @Inject constructor(
    private val tryOnRepository: TryOnRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TryOnUiState())
    val uiState: StateFlow<TryOnUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TryOnNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadWardrobeItems()
    }

    private fun loadWardrobeItems() {
        // TODO: Repository'den gerçek verileri çek (Wardrobe DB / API)
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

    /**
     * Seçili kıyafet ve kişi fotoğrafını FastAPI'ye gönderir.
     *
     * @param personImagePath Kişi fotoğrafının cihazındaki yerel dosya yolu
     * @param garmentImagePath Kıyafet fotoğrafının cihazındaki yerel dosya yolu
     */
    fun onSimulateClicked(
        personImagePath: String,
        garmentImagePath: String
    ) {
        viewModelScope.launch {
            tryOnRepository.generateTryOn(personImagePath, garmentImagePath)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(isSimulating = true, errorMessage = null)
                            }
                        }
                        is Resource.Success -> {
                            _uiState.update { it.copy(isSimulating = false) }
                            _navigationEvent.send(
                                TryOnNavigationEvent.NavigateToSimulationResult(result.data)
                            )
                        }
                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(isSimulating = false, errorMessage = result.message)
                            }
                        }
                    }
                }
        }
    }
}
