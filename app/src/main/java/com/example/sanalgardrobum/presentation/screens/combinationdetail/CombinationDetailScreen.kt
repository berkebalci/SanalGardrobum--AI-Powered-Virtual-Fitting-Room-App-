package com.example.sanalgardrobum.presentation.screens.combinationdetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.GradientBackground
import com.example.sanalgardrobum.presentation.screens.utils.StyleAiTopBar
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun CombinationDetailScreen(
    uiState: CombinationDetailUiState,
    onTabSelected: (String) -> Unit,
    onSaveToggled: () -> Unit,
    onNavigateToSimulation: () -> Unit,
    onBackClick: () -> Unit
) {
    var animateScore by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateScore = true }
    val combo = uiState.combo

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 100.dp)) {
                item {
                    Card(Modifier.fillMaxWidth().height(280.dp).padding(horizontal = 20.dp), shape = CardShape, elevation = CardDefaults.cardElevation(8.dp)) {
                        Box(Modifier.fillMaxSize()) {
                            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple100, Pink50, Blue50))))
                            Box(Modifier.fillMaxSize().background(AppGradients.ImageOverlay))
                            Box(Modifier.align(Alignment.TopStart).padding(16.dp).clip(RoundedCornerShape(50)).background(AppGradients.styleBadgeGradient(combo.style)).padding(horizontal = 12.dp, vertical = 6.dp)) { Text(uiState.styleLabel, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White) }
                            Row(Modifier.align(Alignment.TopEnd).padding(16.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(0.9f)).padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.Star, null, tint = Yellow400, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(4.dp)); Text("${combo.score}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gray800) }
                            Column(Modifier.align(Alignment.BottomStart).padding(20.dp)) {
                                Text(combo.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White); Spacer(Modifier.height(4.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.WbSunny, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text(combo.season, fontSize = 12.sp, color = Color.White.copy(0.8f)) }
                                    Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Checkroom, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text("${combo.items.size} Parça", fontSize = 12.sp, color = Color.White.copy(0.8f)) }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
                item { Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) { Text(combo.description, fontSize = 14.sp, color = Gray600, lineHeight = 22.sp, modifier = Modifier.padding(16.dp)) }; Spacer(Modifier.height(16.dp)) }
                item {
                    Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                        Row(Modifier.padding(4.dp)) {
                            listOf("details" to "Parçalar", "analysis" to "Stil Analizi").forEach { (id, label) ->
                                Box(Modifier.weight(1f).height(40.dp).clip(RoundedCornerShape(12.dp)).then(if (uiState.activeTab == id) Modifier.background(AppGradients.AccentHorizontal) else Modifier).clickable { onTabSelected(id) }, contentAlignment = Alignment.Center) { Text(label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = if (uiState.activeTab == id) Color.White else Gray500) }
                            }
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
                if (uiState.activeTab == "details") {
                    items(combo.items.size) { index ->
                        Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Gray50), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Gray600, modifier = Modifier.size(20.dp)) }
                                Spacer(Modifier.width(16.dp)); Column(Modifier.weight(1f)) { Text(combo.items[index], fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray800); Text("Kumaş", fontSize = 12.sp, color = Gray500) }
                            }
                        }
                    }
                } else {
                    item {
                        Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                            Column(Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.BarChart, null, tint = Purple600, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Uyum Skorları", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Gray800) }
                                Spacer(Modifier.height(16.dp))
                                listOf(Triple("Renk Uyumu", 97, "purple"), Triple("Vücut Uygunluğu", 94, "blue"), Triple("Mevsimsellik", 98, "amber"), Triple("Stil Bütünlüğü", 96, "pink")).forEach { (label, value, color) -> ScoreBreakdownItem(label, value, color, animateScore); Spacer(Modifier.height(12.dp)) }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    item {
                        Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                            Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) { ScoreRing(combo.score, animateScore, Modifier.size(80.dp)); Spacer(Modifier.width(20.dp)); Column { Text("Genel Uyum Skoru", fontWeight = FontWeight.Bold, color = Gray800); Spacer(Modifier.height(4.dp)); Text("Bu kombinasyon yapay zeka tarafından ${if (combo.score >= 95) "mükemmel" else "çok iyi"} olarak değerlendirildi.", fontSize = 12.sp, color = Gray500, lineHeight = 18.sp) } }
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    item {
                        Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                            Column(Modifier.background(AppGradients.AccentSoft).padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(32.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.SmartToy, null, tint = Color.White, modifier = Modifier.size(16.dp)) }; Spacer(Modifier.width(8.dp)); Text("Yapay Zeka Yorumu", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White) }
                                Spacer(Modifier.height(12.dp))
                                listOf("Bu kombinasyon iş ortamında güçlü ve profesyonel bir izlenim bırakır.", "Renk paleti klasik ve zamansız; her toplantıda güven verir.").forEach { Row(Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.Top) { Icon(Icons.Outlined.CheckCircle, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp)); Spacer(Modifier.width(8.dp)); Text(it, fontSize = 12.sp, color = Color.White.copy(0.9f), lineHeight = 18.sp) } }
                            }
                        }
                    }
                }
            }
            Box(Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(Color.White.copy(0.9f)).padding(horizontal = 20.dp, vertical = 16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(Modifier.weight(1f).height(48.dp).clickable { onBackClick() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Gray100)) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Geri Dön", fontWeight = FontWeight.SemiBold, color = Gray700, fontSize = 14.sp) } }
                    Card(Modifier.weight(2f).height(48.dp).clickable { onNavigateToSimulation() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent), elevation = CardDefaults.cardElevation(4.dp)) { Box(Modifier.fillMaxSize().background(AppGradients.AccentHorizontal, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.AutoAwesome, null, tint = Color.White, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Text("Sanal Dene", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp) } } }
                }
            }
            StyleAiTopBar(title = "Kombin Detayı", onBackClick = onBackClick, trailingContent = { Box(Modifier.size(36.dp).clip(CircleShape).then(if (uiState.isSaved) Modifier.background(Pink50) else Modifier).clickable { onSaveToggled() }, contentAlignment = Alignment.Center) { Icon(if (uiState.isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder, "Kaydet", tint = if (uiState.isSaved) Pink500 else Gray700, modifier = Modifier.size(20.dp)) } })
        }
    }
}

@Composable
private fun ScoreBreakdownItem(label: String, value: Int, color: String, animate: Boolean) {
    val f by animateFloatAsState(if (animate) value / 100f else 0f, tween(1000), label = "s")
    Column { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Gray700); Text("$value%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gray800) }; Spacer(Modifier.height(6.dp)); Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)).background(Gray100)) { Box(Modifier.fillMaxWidth(f).height(8.dp).clip(RoundedCornerShape(50)).background(AppGradients.scoreGradient(color))) } }
}

@Composable
private fun ScoreRing(score: Int, animate: Boolean, modifier: Modifier = Modifier) {
    val sweep by animateFloatAsState(if (animate) score * 3.6f else 0f, tween(1200), label = "r")
    Box(modifier, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) { val sw = 8.dp.toPx(); val s = Size(size.width - sw, size.height - sw); val tl = Offset(sw / 2, sw / 2); drawArc(Gray100, 0f, 360f, false, tl, s, style = Stroke(sw, cap = StrokeCap.Round)); drawArc(Brush.sweepGradient(listOf(Purple500, Pink500)), -90f, sweep, false, tl, s, style = Stroke(sw, cap = StrokeCap.Round)) }
        Text("$score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Gray800)
    }
}
