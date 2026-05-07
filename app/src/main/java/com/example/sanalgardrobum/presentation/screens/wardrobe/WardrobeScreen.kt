package com.example.sanalgardrobum.presentation.screens.wardrobe

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.*
import com.example.sanalgardrobum.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardrobeScreen(
    uiState: WardrobeUiState,
    onCategorySelected: (String) -> Unit,
    onItemSelected: (Int) -> Unit,
    onItemDismissed: () -> Unit,
    onNavigateToUpload: () -> Unit,
    onNavigateToCombinations: () -> Unit,
    onNavigateToTryOn: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp, start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item(span = { GridItemSpan(2) }) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf(Triple(uiState.items.size.toString(), "Kıyafet", AppGradients.FeaturePurplePink), Triple("6", "Kombin", AppGradients.FeatureBlueCyan), Triple("4", "Mevsim", AppGradients.FeatureOrangeRed)).forEach { (value, label, gradient) ->
                            Card(Modifier.weight(1f), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                                Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(gradient), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Color.White, modifier = Modifier.size(16.dp)) }
                                    Spacer(Modifier.height(8.dp)); Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gray800); Text(label, fontSize = 10.sp, color = Gray500)
                                }
                            }
                        }
                    }
                }
                item(span = { GridItemSpan(2) }) {
                    Spacer(Modifier.height(4.dp))
                    Card(Modifier.fillMaxWidth().clickable { onNavigateToCombinations() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent), elevation = CardDefaults.cardElevation(4.dp)) {
                        Row(Modifier.background(AppGradients.AccentSoft).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
                            Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text("Kombin Önerisi Al", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White); Text("Yapay zeka gardırobunuzu analiz etsin", fontSize = 12.sp, color = Color.White.copy(0.8f)) }
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(24.dp))
                        }
                    }
                }
                item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(4.dp)); CategoryFilterRow(categories = uiState.categories, selectedCategoryId = uiState.activeCategory, onCategorySelected = onCategorySelected) }
                item(span = { GridItemSpan(2) }) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("${uiState.filteredItems.size} parça", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray700) } }
                items(uiState.filteredItems, key = { it.id }) { item -> WardrobeItemCard(item) { onItemSelected(item.id); scope.launch { sheetState.show() } } }
                if (uiState.filteredItems.isEmpty()) {
                    item(span = { GridItemSpan(2) }) { Column(Modifier.fillMaxWidth().padding(vertical = 48.dp), horizontalAlignment = Alignment.CenterHorizontally) { Box(Modifier.size(64.dp).clip(CircleShape).background(Purple100), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Purple400, modifier = Modifier.size(32.dp)) }; Spacer(Modifier.height(16.dp)); Text("Bu kategoride kıyafet yok", fontSize = 14.sp, color = Gray500) } }
                }
            }
            StyleAiTopBar(title = "Gardırobum", trailingContent = { Box(Modifier.size(36.dp).clip(CircleShape).background(AppGradients.AccentSoft).clickable { onNavigateToUpload() }, contentAlignment = Alignment.Center) { Icon(Icons.Filled.Add, null, tint = Color.White, modifier = Modifier.size(20.dp)) } })
        }
    }

    if (uiState.selectedItemId != null && uiState.selectedItem != null) {
        ModalBottomSheet(onDismissRequest = { onItemDismissed() }, sheetState = sheetState, containerColor = Color.White, shape = BottomSheetShape) {
            ItemDetailSheet(item = uiState.selectedItem!!, onTryOn = { onItemDismissed(); onNavigateToTryOn() }, onClose = { scope.launch { sheetState.hide() }; onItemDismissed() })
        }
    }
}

@Composable
private fun WardrobeItemCard(item: WardrobeItemData, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth().clickable { onClick() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column {
            Box(Modifier.fillMaxWidth().height(140.dp)) {
                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Gray50, Gray100))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Gray400, modifier = Modifier.size(36.dp)) }
                Box(Modifier.align(Alignment.TopEnd).padding(8.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(0.9f)).padding(horizontal = 8.dp, vertical = 2.dp)) { Text(item.season, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Purple700) }
            }
            Column(Modifier.padding(12.dp)) {
                Text(item.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Gray800, maxLines = 1, overflow = TextOverflow.Ellipsis); Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(item.brand, fontSize = 9.sp, color = Gray500); Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(12.dp).clip(CircleShape).background(Gray200)); Spacer(Modifier.width(4.dp)); Text(item.color, fontSize = 9.sp, color = Gray500) } }
            }
        }
    }
}

@Composable
private fun ItemDetailSheet(item: WardrobeItemData, onTryOn: () -> Unit, onClose: () -> Unit) {
    Column(Modifier.padding(20.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.size(width = 112.dp, height = 144.dp).clip(RoundedCornerShape(16.dp)).background(Brush.verticalGradient(listOf(Gray50, Gray100))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Gray400, modifier = Modifier.size(36.dp)) }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Gray800); Text(item.brand, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Purple600); Spacer(Modifier.height(12.dp))
                DetailInfo(Icons.Outlined.LocalOffer, item.category); DetailInfo(Icons.Outlined.WbSunny, item.season); DetailInfo(Icons.Outlined.Palette, item.color)
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(Modifier.weight(1f).height(48.dp).clickable { onTryOn() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) { Box(Modifier.fillMaxSize().background(AppGradients.AccentHorizontal, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Text("Dene", fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 14.sp) } } }
            Card(Modifier.weight(1f).height(48.dp).clickable { onClose() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Gray100)) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Close, null, tint = Gray700, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Text("Kapat", fontWeight = FontWeight.SemiBold, color = Gray700, fontSize = 14.sp) } } }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun DetailInfo(icon: ImageVector, text: String) {
    Row(Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = Gray400, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(8.dp)); Text(text, fontSize = 12.sp, color = Gray600) }
}
