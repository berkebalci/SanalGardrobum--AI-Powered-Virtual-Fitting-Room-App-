package com.example.sanalgardrobum.presentation.screens.tryon

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.common.*
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun TryOnScreen(
    uiState: TryOnUiState,
    onCategorySelected: (String) -> Unit,
    onClothToggled: (String) -> Unit,
    onSimulateClicked: () -> Unit,
    onNavigateToWardrobe: () -> Unit,
    onBackClick: () -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(top = 80.dp, bottom = 80.dp)) {
                Card(modifier = Modifier.fillMaxWidth().height(200.dp).padding(horizontal = 20.dp), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple50, Pink50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Purple400, modifier = Modifier.size(48.dp)) }
                        if (uiState.selectedCount > 0) {
                            Row(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).clip(RoundedCornerShape(50)).background(Purple600).padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp))
                                Text("${uiState.selectedCount} Seçili", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                CategoryFilterRow(categories = uiState.categories, selectedCategoryId = uiState.selectedCategory, onCategorySelected = onCategorySelected, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(16.dp))
                Row(Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.ShoppingBag, null, tint = Purple600, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Gardırobunuz", fontWeight = FontWeight.Bold, color = Gray800) }
                Spacer(Modifier.height(12.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f).padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.filteredItems, key = { it.id }) { item ->
                        ClothingItemCard(item = item, isSelected = item.id in uiState.selectedClothIds, onClick = { onClothToggled(item.id) })
                    }
                }
                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                    Row(modifier = Modifier.background(AppGradients.InfoCard).padding(16.dp), verticalAlignment = Alignment.Top) {
                        Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Lightbulb, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
                        Spacer(Modifier.width(12.dp))
                        Column { Text("Nasıl Çalışır?", fontWeight = FontWeight.SemiBold, color = Color.White); Spacer(Modifier.height(4.dp)); Text("Denemek istediğiniz kıyafetleri seçin. Yapay zeka, seçtiğiniz kıyafetleri vücudunuza uygun şekilde hizalayacak.", fontSize = 12.sp, color = Color.White.copy(0.9f), lineHeight = 18.sp) }
                    }
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color.White).padding(horizontal = 20.dp, vertical = 16.dp)) {
                GradientButton(text = if (uiState.isSimulating) "Simüle Ediliyor..." else "Sanal Deneme Yap (${uiState.selectedCount})", onClick = onSimulateClicked, icon = Icons.Outlined.AutoAwesome, enabled = uiState.canSimulate, isLoading = uiState.isSimulating)
            }
            StyleAiTopBar(title = "Sanal Deneme", onBackClick = onBackClick, trailingContent = { Text("Gardırop", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Purple600, modifier = Modifier.clickable { onNavigateToWardrobe() }) })
        }
    }
}

@Composable
private fun ClothingItemCard(item: ClothItem, isSelected: Boolean, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp), border = if (isSelected) BorderStroke(3.dp, Purple500) else null) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Gray50, Gray100))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Gray400, modifier = Modifier.size(36.dp)) }
                if (isSelected) { Box(modifier = Modifier.fillMaxSize().background(Purple600.copy(0.2f)), contentAlignment = Alignment.Center) { Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Purple600), contentAlignment = Alignment.Center) { Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(24.dp)) } } }
            }
            Column(Modifier.padding(12.dp)) {
                Text(item.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray800); Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Palette, null, tint = Gray500, modifier = Modifier.size(12.dp)); Spacer(Modifier.width(4.dp)); Text(item.color, fontSize = 12.sp, color = Gray500); Spacer(Modifier.width(8.dp)); Text("•", color = Gray400); Spacer(Modifier.width(8.dp)); Icon(Icons.Outlined.WbSunny, null, tint = Gray500, modifier = Modifier.size(12.dp)); Spacer(Modifier.width(4.dp)); Text(item.season, fontSize = 12.sp, color = Gray500) }
            }
        }
    }
}
