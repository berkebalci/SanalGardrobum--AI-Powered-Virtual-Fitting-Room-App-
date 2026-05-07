package com.example.sanalgardrobum.presentation.screens.combinations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.*
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun CombinationsScreen(
    uiState: CombinationsUiState,
    onFilterSelected: (String) -> Unit,
    onCombinationClicked: (Int) -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp)) {
                item {
                    CategoryFilterRow(categories = uiState.filters, selectedCategoryId = uiState.activeFilter, onCategorySelected = onFilterSelected, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.height(16.dp))
                }
                item {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.Transparent), elevation = CardDefaults.cardElevation(4.dp)) {
                        Row(Modifier.background(AppGradients.AccentSoft).padding(20.dp), verticalAlignment = Alignment.Top) {
                            Box(Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(24.dp)) }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Size Özel Öneriler", fontWeight = FontWeight.Bold, color = Color.White); Spacer(Modifier.height(4.dp))
                                Text("Yapay zeka, vücut tipiniz ve gardırobunuza göre en uyumlu kombinleri seçti", fontSize = 12.sp, color = Color.White.copy(0.9f), lineHeight = 18.sp)
                                Spacer(Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    listOf("${uiState.filteredCombinations.size} Kombin", "%95 Uyum").forEach { label -> Box(Modifier.clip(RoundedCornerShape(50)).background(Color.White.copy(0.2f)).padding(horizontal = 12.dp, vertical = 4.dp)) { Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White) } }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
                items(uiState.filteredCombinations, key = { it.id }) { combo ->
                    CombinationCard(combo, onClick = { onCombinationClicked(combo.id) }, modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(Modifier.height(16.dp))
                }
            }
            StyleAiTopBar(title = "Kombin Önerileri", trailingIcon = Icons.Outlined.FilterList, onTrailingClick = { })
        }
    }
}

@Composable
private fun CombinationCard(combo: CombinationData, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth().clickable { onClick() }, shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column {
            Box(Modifier.fillMaxWidth().height(240.dp)) {
                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple50, Pink50, Blue50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.AutoAwesome, null, tint = Purple400, modifier = Modifier.size(48.dp)) }
                Row(Modifier.align(Alignment.TopEnd).padding(16.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(0.9f)).padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.Star, null, tint = Yellow400, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(4.dp)); Text("${combo.score}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gray800) }
                Box(Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.7f)))).padding(20.dp)) {
                    Column { Text(combo.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White); Spacer(Modifier.height(4.dp)); Text(combo.description, fontSize = 12.sp, color = Color.White.copy(0.9f), maxLines = 2, overflow = TextOverflow.Ellipsis) }
                }
            }
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.LocalOffer, null, tint = Gray600, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text(combo.style.replaceFirstChar { it.uppercase() }, fontSize = 12.sp, color = Gray600) }
                    Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.WbSunny, null, tint = Gray600, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text(combo.season, fontSize = 12.sp, color = Gray600) }
                }
                Spacer(Modifier.height(12.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) { combo.items.forEach { item -> Box(Modifier.clip(RoundedCornerShape(50)).background(Purple50).padding(horizontal = 12.dp, vertical = 4.dp)) { Text(item, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Purple700) } } }
            }
        }
    }
}
