package com.example.sanalgardrobum.presentation.screens.simulationresult

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.GradientBackground
import com.example.sanalgardrobum.presentation.screens.utils.StyleAiTopBar
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun SimulationResultScreen(
    onNavigateToCombinations: () -> Unit,
    onNavigateToTryOn: () -> Unit,
    onBackClick: () -> Unit
) {
    // animateScores is purely UI animation state - allowed in composable
    var animateScores by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateScores = true }

    val scores = listOf(ScoreItem("Doğruluk", 96, Icons.Outlined.Star, "purple"), ScoreItem("Uyum Skoru", 94, Icons.Outlined.Straighten, "blue"), ScoreItem("Stil Skoru", 92, Icons.Outlined.Palette, "pink"), ScoreItem("Renk Uyumu", 95, Icons.Outlined.Contrast, "green"))
    val triedClothes = listOf("Beyaz Gömlek", "Siyah Pantolon", "Mavi Kot Ceket")

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)) {
                item {
                    Card(modifier = Modifier.fillMaxWidth().height(320.dp), shape = CardShape, elevation = CardDefaults.cardElevation(8.dp)) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple50, Pink50, Blue50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Purple400, modifier = Modifier.size(60.dp)) }
                            Row(modifier = Modifier.fillMaxWidth().padding(16.dp).align(Alignment.TopCenter), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(modifier = Modifier.clip(RoundedCornerShape(50)).background(Green500).padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.CheckCircle, null, tint = Color.White, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text("Simülasyon Tamamlandı", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White) }
                                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.White.copy(0.9f)).clickable { }, contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Download, "İndir", tint = Gray700, modifier = Modifier.size(18.dp)) }
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column(Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Star, null, tint = Purple600, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Uyum Skorları", fontWeight = FontWeight.Bold, color = Gray800) }
                            Spacer(Modifier.height(16.dp))
                            scores.forEach { score -> ScoreBar(score, animateScores); Spacer(Modifier.height(16.dp)) }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column(Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Checkroom, null, tint = Purple600, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Denenen Kıyafetler", fontWeight = FontWeight.Bold, color = Gray800) }
                            Spacer(Modifier.height(16.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                triedClothes.forEach { name ->
                                    Card(Modifier.weight(1f), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Gray50)) {
                                        Column { Box(Modifier.fillMaxWidth().height(80.dp).background(Brush.verticalGradient(listOf(Purple50, Gray50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Checkroom, null, tint = Gray400, modifier = Modifier.size(24.dp)) }; Text(name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Gray700, modifier = Modifier.padding(8.dp), maxLines = 1) }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Card(Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                        Column(Modifier.background(AppGradients.AccentSoft).padding(20.dp)) {
                            Row(verticalAlignment = Alignment.Top) {
                                Box(Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.SmartToy, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
                                Spacer(Modifier.width(12.dp))
                                Column { Text("Yapay Zeka Analizi", fontWeight = FontWeight.SemiBold, color = Color.White); Spacer(Modifier.height(4.dp)); Text("Seçtiğiniz kıyafetler vücut yapınıza çok uygun! Renk uyumu mükemmel ve stil tercihiniz harika.", fontSize = 12.sp, color = Color.White.copy(0.9f), lineHeight = 18.sp) }
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Card(Modifier.weight(1f).height(52.dp).clickable { onNavigateToCombinations() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) { Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Palette, null, tint = Gray700, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Kombin Önerileri", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray700) } }
                        Card(Modifier.weight(1f).height(52.dp).clickable { onNavigateToTryOn() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) { Row(Modifier.fillMaxSize().background(AppGradients.AccentHorizontal, RoundedCornerShape(16.dp)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Refresh, null, tint = Color.White, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("Yeni Deneme", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White) } }
                    }
                }
            }
            StyleAiTopBar(title = "Deneme Sonucu", onBackClick = onBackClick)
        }
    }
}

private data class ScoreItem(val label: String, val value: Int, val icon: ImageVector, val color: String)

@Composable
private fun ScoreBar(score: ScoreItem, animate: Boolean) {
    val animatedWidth by animateFloatAsState(targetValue = if (animate) score.value / 100f else 0f, animationSpec = tween(1000), label = "score")
    val iconColor = when (score.color) { "purple" -> Purple600; "blue" -> Blue500; "pink" -> Pink600; "green" -> Green600; else -> Purple600 }
    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(score.icon, null, tint = iconColor, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Text(score.label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray700) }; Text("${score.value}%", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Gray800) }
        Spacer(Modifier.height(8.dp))
        Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)).background(Gray100)) { Box(Modifier.fillMaxWidth(animatedWidth).height(8.dp).clip(RoundedCornerShape(50)).background(AppGradients.scoreGradient(score.color))) }
    }
}
