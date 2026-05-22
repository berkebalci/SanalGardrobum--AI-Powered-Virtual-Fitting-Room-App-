package com.example.sanalgardrobum.presentation.screens.wardrobe

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sanalgardrobum.domain.model.Garment
import com.example.sanalgardrobum.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sanalgardrobum.domain.usecase.garment.AddGarmentUseCase
import com.example.sanalgardrobum.domain.usecase.garment.DeleteGarmentUseCase
import com.example.sanalgardrobum.domain.usecase.garment.GetGarmentsUseCase
import com.example.sanalgardrobum.domain.usecase.garment.GetGarmentsByCategoryUseCase
import com.example.sanalgardrobum.presentation.screens.utils.FilterCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WardrobeItemData(
    val id: Long,
    val category: String,
    val name: String,
    val imagePath: String,
    val createdAt: Long = 0L
)

data class WardrobeUiState(
    val activeCategory: String = "all",
    val items: List<WardrobeItemData> = emptyList(),
    val selectedItemId: Long? = null,
    val categories: List<FilterCategory> = defaultCategories,
    val isLoading: Boolean = false,
    // Add Garment Dialog state
    val isAddDialogVisible: Boolean = false,
    val newGarmentUri: Uri? = null,
    val newGarmentName: String = "",
    val newGarmentCategory: String = "top",
    val isAdding: Boolean = false,
    val errorMessage: String? = null
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

@HiltViewModel
class WardrobeViewModel @Inject constructor(
    private val getGarmentsUseCase: GetGarmentsUseCase,
    private val getGarmentsByCategoryUseCase: GetGarmentsByCategoryUseCase,
    private val addGarmentUseCase: AddGarmentUseCase,
    private val deleteGarmentUseCase: DeleteGarmentUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WardrobeUiState())
    val uiState: StateFlow<WardrobeUiState> = _uiState.asStateFlow()

    private val currentUserId: String?
        get() = getCurrentUserUseCase()?.uid

    init {
        loadWardrobeItems()
    }

    private fun loadWardrobeItems() {
        val userId = currentUserId ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getGarmentsUseCase(userId).collect { garments ->
                _uiState.update { state ->
                    state.copy(
                        items = garments.map { it.toWardrobeItem() },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onCategorySelected(categoryId: String) {
        _uiState.update { it.copy(activeCategory = categoryId) }
    }

    fun onItemSelected(itemId: Long) {
        _uiState.update { it.copy(selectedItemId = itemId) }
    }

    fun onItemDismissed() {
        _uiState.update { it.copy(selectedItemId = null) }
    }

    // ── Add Garment Dialog ──────────────────────────────────────────────

    fun onAddClicked() {
        _uiState.update { it.copy(isAddDialogVisible = true) }
    }

    fun onAddDialogDismissed() {
        _uiState.update {
            it.copy(
                isAddDialogVisible = false,
                newGarmentUri = null,
                newGarmentName = "",
                newGarmentCategory = "top",
                errorMessage = null
            )
        }
    }

    fun onPhotoSelected(uri: Uri) {
        _uiState.update { it.copy(newGarmentUri = uri) }
    }

    fun onGarmentNameChanged(name: String) {
        _uiState.update { it.copy(newGarmentName = name) }
    }

    fun onGarmentCategoryChanged(category: String) {
        _uiState.update { it.copy(newGarmentCategory = category) }
    }

    fun onConfirmAdd() {
        val userId = currentUserId ?: return
        val uri = _uiState.value.newGarmentUri ?: return
        val name = _uiState.value.newGarmentName.ifBlank { return }
        val category = _uiState.value.newGarmentCategory

        viewModelScope.launch {
            _uiState.update { it.copy(isAdding = true) }
            try {
                addGarmentUseCase(userId, uri, name, category)
                onAddDialogDismissed()
                _uiState.update { it.copy(isAdding = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isAdding = false,
                        errorMessage = e.localizedMessage ?: "Kıyafet eklenemedi"
                    )
                }
            }
        }
    }

    // ── Delete Garment ──────────────────────────────────────────────────

    fun onDeleteItem(itemId: Long) {
        val userId = currentUserId ?: return
        val item = _uiState.value.items.find { it.id == itemId } ?: return

        viewModelScope.launch {
            try {
                deleteGarmentUseCase(
                    Garment(
                        id = item.id,
                        userId = userId,
                        category = item.category,
                        imagePath = item.imagePath,
                        name = item.name,
                        createdAt = item.createdAt
                    )
                )
                _uiState.update { it.copy(selectedItemId = null) }
            } catch (_: Exception) {
                // Silme hatası sessizce yutulur
            }
        }
    }

    private fun Garment.toWardrobeItem() = WardrobeItemData(
        id = id,
        category = category,
        name = name,
        imagePath = imagePath,
        createdAt = createdAt
    )
}
