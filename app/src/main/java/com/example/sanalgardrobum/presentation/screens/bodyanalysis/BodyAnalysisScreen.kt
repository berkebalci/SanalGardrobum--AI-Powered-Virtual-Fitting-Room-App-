package com.example.sanalgardrobum.presentation.screens.bodyanalysis

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.utils.GradientBackground
import com.example.sanalgardrobum.presentation.screens.utils.StyleAiTopBar
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun BodyAnalysisScreen(
    uiState: BodyAnalysisUiState,
    onBackClick: () -> Unit
) {
    val animatedProgress by animateFloatAsState(targetValue = uiState.progress, animationSpec = tween(300), label = "progress")

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)) {
                item {
                    Card(modifier = Modifier.fillMaxWidth().height(280.dp), shape = CardShape, elevation = CardDefaults.cardElevation(4.dp)) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple50, Pink50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Person, null, tint = Purple400, modifier = Modifier.size(60.dp)) }
                            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.5f)))))
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("Analiz İlerlemesi", fontWeight = FontWeight.Bold, color = Gray800)
                                Text("${(animatedProgress * 100).toInt()}%", fontSize = 24.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.displayMedium.copy(brush = AppGradients.AccentHorizontal))
                            }
                            Spacer(Modifier.height(12.dp))
                            LinearProgressIndicator(progress = { animatedProgress }, modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(50)), color = Purple500, trackColor = Gray100, strokeCap = StrokeCap.Round)
                            Spacer(Modifier.height(16.dp))
                            uiState.steps.forEachIndexed { index, label ->
                                val isCompleted = index < uiState.currentStep
                                val isUpcoming = index > uiState.currentStep
                                Row(Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(brush = if (isCompleted) Brush.horizontalGradient(listOf(Green500, Green500)) else if (index == uiState.currentStep) AppGradients.AccentSoft else Brush.horizontalGradient(listOf(Gray200, Gray200))), contentAlignment = Alignment.Center) {
                                        if (isCompleted) Icon(Icons.Filled.Check, null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isUpcoming) Gray400 else Gray800)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    AnimatedVisibility(visible = uiState.progress > 0.5f, enter = fadeIn() + slideInVertically(initialOffsetY = { it / 4 })) { AnalysisResultsCard() }
                    Spacer(Modifier.height(24.dp))
                }
                item { AiInfoBanner() }
            }
            StyleAiTopBar(title = "Vücut Analizi", onBackClick = onBackClick)
        }
    }
}

@Composable
private fun AnalysisResultsCard() {
    val results = listOf(Triple(Icons.Outlined.Person, "Vücut Tipi", "Kum Saati"), Triple(Icons.Outlined.SwapHoriz, "Omuz Genişliği", "42 cm"), Triple(Icons.Outlined.Straighten, "Bel Oranı", "0.68"), Triple(Icons.Outlined.FavoriteBorder, "Boy/Kilo Oranı", "İdeal"))
    Card(modifier = Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.BarChart, null, tint = Purple600, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(8.dp)); Text("Analiz Sonuçları", fontWeight = FontWeight.Bold, color = Gray800) }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                results.chunked(2).forEach { column -> Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) { column.forEach { (icon, label, value) -> ResultItem(icon, label, value) } } }
            }
        }
    }
}

@Composable
private fun ResultItem(icon: ImageVector, label: String, value: String) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column(modifier = Modifier.background(Brush.linearGradient(listOf(Purple50, Pink50))).padding(16.dp)) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Color.White), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Purple600, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.height(8.dp)); Text(label, fontSize = 12.sp, color = Gray600); Spacer(Modifier.height(4.dp)); Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Gray800)
        }
    }
}

@Composable
private fun AiInfoBanner() {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Row(modifier = Modifier.background(AppGradients.AccentSoft).padding(16.dp), verticalAlignment = Alignment.Top) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.SmartToy, null, tint = Color.White, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.width(12.dp))
            Column { Text("Yapay Zeka Analizi", fontWeight = FontWeight.SemiBold, color = Color.White); Spacer(Modifier.height(4.dp)); Text("Gelişmiş yapay zeka algoritmaları ile vücut segmentasyonu, poz analizi ve oran hesaplaması yapılıyor.", fontSize = 12.sp, color = Color.White.copy(0.9f), lineHeight = 18.sp) }
        }
    }
}
