package com.example.sanalgardrobum.presentation.screens.wardrobe

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.*
import com.example.sanalgardrobum.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardrobeScreen(
    uiState: WardrobeUiState,
    onCategorySelected: (String) -> Unit,
    onItemSelected: (Long) -> Unit,
    onItemDismissed: () -> Unit,
    onNavigateToUpload: () -> Unit,
    onNavigateToCombinations: () -> Unit,
    onNavigateToTryOn: () -> Unit,
    onAddClicked: () -> Unit,
    onPhotoSelected: (Uri) -> Unit,
    onGarmentNameChanged: (String) -> Unit,
    onGarmentCategoryChanged: (String) -> Unit,
    onConfirmAdd: () -> Unit,
    onAddDialogDismissed: () -> Unit,
    onDeleteItem: (Long) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Galeri launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onPhotoSelected(it) }
    }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ── Stats Row ───────────────────────────────────────────
                item(span = { GridItemSpan(2) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        listOf(
                            Triple(uiState.items.size.toString(), "Kıyafet", AppGradients.FeaturePurplePink),
                            Triple("6", "Kombin", AppGradients.FeatureBlueCyan),
                            Triple("4", "Mevsim", AppGradients.FeatureOrangeRed)
                        ).forEach { (value, label, gradient) ->
                            Card(
                                Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(1.dp)
                            ) {
                                Column(
                                    Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(gradient),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Outlined.Checkroom, null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gray800)
                                    Text(label, fontSize = 10.sp, color = Gray500)
                                }
                            }
                        }
                    }
                }

                // ── AI Suggestion Banner ────────────────────────────────
                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(4.dp))
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToCombinations() },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            Modifier
                                .background(AppGradients.AccentSoft)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text("Kombin Önerisi Al", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                                Text("Yapay zeka gardırobunuzu analiz etsin", fontSize = 12.sp, color = Color.White.copy(0.8f))
                            }
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight, null,
                                tint = Color.White.copy(0.8f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // ── Category Filter ─────────────────────────────────────
                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(4.dp))
                    CategoryFilterRow(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.activeCategory,
                        onCategorySelected = onCategorySelected
                    )
                }

                // ── Item Count ──────────────────────────────────────────
                item(span = { GridItemSpan(2) }) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${uiState.filteredItems.size} parça",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Gray700
                        )
                    }
                }

                // ── Garment Grid ────────────────────────────────────────
                items(uiState.filteredItems, key = { it.id }) { item ->
                    WardrobeItemCard(item) {
                        onItemSelected(item.id)
                        scope.launch { sheetState.show() }
                    }
                }

                // ── Empty State ─────────────────────────────────────────
                if (uiState.filteredItems.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Purple100),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.Checkroom, null,
                                    tint = Purple400,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Bu kategoride kıyafet yok",
                                fontSize = 14.sp,
                                color = Gray500
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Yeni kıyafet eklemek için + butonuna tıklayın",
                                fontSize = 12.sp,
                                color = Gray400
                            )
                        }
                    }
                }
            }

            // ── Top Bar ─────────────────────────────────────────────────
            StyleAiTopBar(
                title = "Gardırobum",
                trailingContent = {
                    Box(
                        Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AppGradients.AccentSoft)
                            .clickable { onAddClicked() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Add, null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
            )
        }
    }

    // ── Item Detail Bottom Sheet ─────────────────────────────────────────
    if (uiState.selectedItemId != null && uiState.selectedItem != null) {
        ModalBottomSheet(
            onDismissRequest = { onItemDismissed() },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = BottomSheetShape
        ) {
            ItemDetailSheet(
                item = uiState.selectedItem!!,
                onTryOn = { onItemDismissed(); onNavigateToTryOn() },
                onDelete = {
                    onDeleteItem(uiState.selectedItem!!.id)
                    scope.launch { sheetState.hide() }
                },
                onClose = {
                    scope.launch { sheetState.hide() }
                    onItemDismissed()
                }
            )
        }
    }

    // ── Add Garment Dialog ──────────────────────────────────────────────
    if (uiState.isAddDialogVisible) {
        AddGarmentDialog(
            uiState = uiState,
            onPickPhoto = { galleryLauncher.launch("image/*") },
            onNameChanged = onGarmentNameChanged,
            onCategoryChanged = onGarmentCategoryChanged,
            onConfirm = onConfirmAdd,
            onDismiss = onAddDialogDismissed
        )
    }
}

// ── Garment Card with Image ─────────────────────────────────────────────

@Composable
private fun WardrobeItemCard(item: WardrobeItemData, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                val file = remember(item.imagePath) { File(item.imagePath) }
                if (file.exists()) {
                    val bitmap = remember(item.imagePath) {
                        BitmapFactory.decodeFile(item.imagePath)?.asImageBitmap()
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = item.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        PlaceholderImage()
                    }
                } else {
                    PlaceholderImage()
                }

                // Kategori badge
                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White.copy(0.9f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        getCategoryLabel(item.category),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple700
                    )
                }
            }
            Column(Modifier.padding(12.dp)) {
                Text(
                    item.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray800,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun PlaceholderImage() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Gray50, Gray100))),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Outlined.Checkroom, null, tint = Gray400, modifier = Modifier.size(36.dp))
    }
}

// ── Item Detail Bottom Sheet ────────────────────────────────────────────

@Composable
private fun ItemDetailSheet(
    item: WardrobeItemData,
    onTryOn: () -> Unit,
    onDelete: () -> Unit,
    onClose: () -> Unit
) {
    Column(Modifier.padding(20.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Box(
                Modifier
                    .size(width = 112.dp, height = 144.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                val file = remember(item.imagePath) { File(item.imagePath) }
                if (file.exists()) {
                    val bitmap = remember(item.imagePath) {
                        BitmapFactory.decodeFile(item.imagePath)?.asImageBitmap()
                    }
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = item.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        PlaceholderImage()
                    }
                } else {
                    PlaceholderImage()
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Gray800)
                Spacer(Modifier.height(8.dp))
                DetailInfo(Icons.Outlined.LocalOffer, getCategoryLabel(item.category))
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Dene butonu
            Card(
                Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clickable { onTryOn() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(AppGradients.AccentHorizontal, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Dene", fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 14.sp)
                    }
                }
            }
            // Sil butonu
            Card(
                Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clickable { onDelete() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Red50)
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Delete, null, tint = Red600, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Sil", fontWeight = FontWeight.SemiBold, color = Red600, fontSize = 14.sp)
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun DetailInfo(icon: ImageVector, text: String) {
    Row(
        Modifier.padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Gray400, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 12.sp, color = Gray600)
    }
}

// ── Add Garment Dialog ──────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddGarmentDialog(
    uiState: WardrobeUiState,
    onPickPhoto: () -> Unit,
    onNameChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Kıyafet Ekle",
                fontWeight = FontWeight.Bold,
                color = Gray800
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Fotoğraf Seç
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clickable { onPickPhoto() },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.newGarmentUri != null) Green50 else Gray50
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.newGarmentUri != null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.CheckCircle, null,
                                    tint = Green500,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Fotoğraf seçildi", fontSize = 12.sp, color = Green700)
                                Text(
                                    "Değiştirmek için tıklayın",
                                    fontSize = 10.sp,
                                    color = Gray400
                                )
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.AddPhotoAlternate, null,
                                    tint = Gray400,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Fotoğraf Seç", fontSize = 12.sp, color = Gray500)
                            }
                        }
                    }
                }

                // Kıyafet İsmi
                OutlinedTextField(
                    value = uiState.newGarmentName,
                    onValueChange = onNameChanged,
                    label = { Text("Kıyafet İsmi") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Kategori Seçimi
                Text("Kategori", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Gray600)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf("top" to "Üst", "bottom" to "Alt", "dress" to "Elbise")
                    categories.forEach { (id, label) ->
                        FilterChip(
                            selected = uiState.newGarmentCategory == id,
                            onClick = { onCategoryChanged(id) },
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf("outerwear" to "Dış Giyim", "shoes" to "Ayakkabı", "accessory" to "Aksesuar")
                    categories.forEach { (id, label) ->
                        FilterChip(
                            selected = uiState.newGarmentCategory == id,
                            onClick = { onCategoryChanged(id) },
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }

                // Error message
                if (uiState.errorMessage != null) {
                    Text(
                        uiState.errorMessage,
                        color = Red600,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Card(
                modifier = Modifier
                    .clickable(enabled = !uiState.isAdding && uiState.newGarmentUri != null && uiState.newGarmentName.isNotBlank()) {
                        onConfirm()
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    Modifier
                        .background(
                            if (uiState.newGarmentUri != null && uiState.newGarmentName.isNotBlank())
                                AppGradients.AccentHorizontal
                            else
                                Brush.horizontalGradient(listOf(Gray300, Gray300)),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    if (uiState.isAdding) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Ekle", fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal", color = Gray500)
            }
        }
    )
}

private fun getCategoryLabel(category: String): String = when (category) {
    "top" -> "Üst"
    "bottom" -> "Alt"
    "dress" -> "Elbise"
    "outerwear" -> "Dış Giyim"
    "shoes" -> "Ayakkabı"
    "accessory" -> "Aksesuar"
    else -> category
}
