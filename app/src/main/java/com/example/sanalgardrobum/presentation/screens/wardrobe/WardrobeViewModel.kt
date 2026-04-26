package com.example.sanalgardrobum.presentation.screens.wardrobe

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.lifecycle.ViewModel
import com.example.sanalgardrobum.presentation.screens.common.FilterCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class WardrobeItemData(
    val id: Int,
    val category: String,
    val name: String,
    val color: String,
    val season: String,
    val brand: String
)

data class WardrobeUiState(
    val activeCategory: String = "all",
    val items: List<WardrobeItemData> = emptyList(),
    val selectedItemId: Int? = null,
    val categories: List<FilterCategory> = defaultCategories
) {
    val filteredItems: List<WardrobeItemData>
        get() = if (activeCategory == "all") items
        else items.filter { it.category == activeCategory }

    val selectedItem: WardrobeItemData?
        get() = items.find { it.id == selectedItemId }

    companion object {
        private val defaultCategories = listOf(
            FilterCategory("all", "Tümü", Icons.Outlined.GridView),
            FilterCategory("top", "Üst", Icons.Outlined.Checkroom),
            FilterCategory("bottom", "Alt", Icons.Outlined.Checkroom),
            FilterCategory("dress", "Elbise", Icons.Outlined.Checkroom),
            FilterCategory("outerwear", "Dış Giyim", Icons.Outlined.Checkroom),
            FilterCategory("shoes", "Ayakkabı", Icons.Outlined.Checkroom),
            FilterCategory("accessory", "Aksesuar", Icons.Outlined.ShoppingBag)
        )
    }
}

class WardrobeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WardrobeUiState())
    val uiState: StateFlow<WardrobeUiState> = _uiState.asStateFlow()

    init {
        loadWardrobeItems()
    }

    private fun loadWardrobeItems() {
        // TODO: Repository'den gerçek verileri çek
        val mockItems = listOf(
            WardrobeItemData(1, "top", "Beyaz Oversize Gömlek", "Beyaz", "Tüm Mevsim", "Zara"),
            WardrobeItemData(2, "bottom", "Slim Fit Siyah Pantolon", "Siyah", "Tüm Mevsim", "Mango"),
            WardrobeItemData(3, "outerwear", "Camel Trençkot", "Camel", "Sonbahar", "H&M"),
            WardrobeItemData(4, "dress", "Çiçekli Midi Elbise", "Çok Renkli", "Yaz", "Zara"),
            WardrobeItemData(5, "top", "Gri Kaşmir Kazak", "Gri", "Kış", "COS"),
            WardrobeItemData(6, "bottom", "Yüksek Bel Kot", "Mavi", "Tüm Mevsim", "Levi's"),
            WardrobeItemData(7, "shoes", "Beyaz Deri Sneaker", "Beyaz", "Tüm Mevsim", "Nike"),
            WardrobeItemData(8, "accessory", "Altın Zincir Kolye", "Altın", "Tüm Mevsim", "Mango"),
            WardrobeItemData(9, "outerwear", "Siyah Deri Ceket", "Siyah", "Sonbahar", "Zara"),
            WardrobeItemData(10, "dress", "Siyah Kokteyl Elbise", "Siyah", "Tüm Mevsim", "H&M")
        )
        _uiState.update { it.copy(items = mockItems) }
    }

    fun onCategorySelected(categoryId: String) {
        _uiState.update { it.copy(activeCategory = categoryId) }
    }

    fun onItemSelected(itemId: Int) {
        _uiState.update { it.copy(selectedItemId = itemId) }
    }

    fun onItemDismissed() {
        _uiState.update { it.copy(selectedItemId = null) }
    }
}
