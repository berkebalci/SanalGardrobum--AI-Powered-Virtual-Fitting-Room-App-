package com.example.sanalgardrobum.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.common.GradientBackground
import com.example.sanalgardrobum.presentation.screens.common.StyleAiTopBar
import com.example.sanalgardrobum.ui.theme.*

/**
 * Ana Sayfa ekranı — Pure UI, sıfır logic.
 * Tüm navigation işlemleri callback lambda'lar ile dışarıya delege edilir.
 */
@Composable
fun HomeScreen(
    onNavigateToUpload: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCombinations: () -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Yapay Zeka ile\nSanal Gardırop",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Gray800,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Kıyafetlerinizi sanal olarak deneyin ve\nsize özel stil önerileri alın",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray600,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                item {
                    HeroBanner(
                        onCtaClick = onNavigateToUpload,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    FeaturesGrid(modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    HowItWorksSection(modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    StatsRow(modifier = Modifier.padding(horizontal = 20.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    PopularCombinations(onSeeAllClick = onNavigateToCombinations)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            /*StyleAiTopBar(
                title = "",
                trailingIcon = Icons.Outlined.Settings,
                onTrailingClick = onNavigateToSettings
            )*/
        }
    }
}

// ─── Sub-composables (stateless, private) ────────────────────────────────────

@Composable
private fun HeroBanner(onCtaClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().height(320.dp), shape = CardShape, elevation = CardDefaults.cardElevation(8.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple100, Pink50, Blue50))))
            Box(modifier = Modifier.fillMaxSize().background(AppGradients.ImageOverlay))
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp).fillMaxWidth().height(52.dp)
                    .clip(RoundedCornerShape(16.dp)).background(Color.White).clickable { onCtaClick() },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.CameraAlt, null, tint = Gray800, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Fotoğraf Yükle ve Başla", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Gray800)
                }
            }
        }
    }
}

private data class FeatureItem(val icon: ImageVector, val title: String, val description: String, val gradient: Brush)

@Composable
private fun FeaturesGrid(modifier: Modifier = Modifier) {
    val features = listOf(
        FeatureItem(Icons.Outlined.CameraAlt, "Fotoğraf Yükle", "Boydan fotoğrafınızı yükleyin", AppGradients.FeaturePurplePink),
        FeatureItem(Icons.Outlined.Person, "Vücut Analizi", "Yapay zeka ile vücut analizi", AppGradients.FeatureBlueCyan),
        FeatureItem(Icons.Outlined.Checkroom, "Sanal Deneme", "Kıyafetleri üzerinizde görün", AppGradients.FeatureGreenEmerald),
        FeatureItem(Icons.Outlined.Palette, "Kombin Önerileri", "Size özel stil önerileri", AppGradients.FeatureOrangeRed)
    )
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (row in features.chunked(2)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { feature ->
                    Card(
                        modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(feature.gradient), contentAlignment = Alignment.Center) {
                                Icon(feature.icon, null, tint = Color.White, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(feature.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray800)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(feature.description, fontSize = 12.sp, color = Gray500, lineHeight = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HowItWorksSection(modifier: Modifier = Modifier) {
    val steps = listOf(
        "Boydan fotoğrafınızı yükleyin", "Yapay zeka vücut analizinizi yapar",
        "Gardırobunuzdaki kıyafetleri deneyin", "Size özel kombin önerileri alın"
    )
    Card(modifier = modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Lightbulb, null, tint = Purple600, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nasıl Çalışır?", style = MaterialTheme.typography.titleLarge, color = Gray800)
            }
            Spacer(modifier = Modifier.height(16.dp))
            steps.forEachIndexed { index, text ->
                Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
                    Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(AppGradients.AccentSoft), contentAlignment = Alignment.Center) {
                        Text("${index + 1}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text, style = MaterialTheme.typography.bodyMedium, color = Gray700, modifier = Modifier.padding(top = 6.dp))
                }
            }
        }
    }
}

@Composable
private fun StatsRow(modifier: Modifier = Modifier) {
    val stats = listOf("10K+" to "Kullanıcı", "50K+" to "Deneme", "98%" to "Memnuniyet")
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        stats.forEach { (value, label) ->
            Card(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.displayMedium.copy(brush = AppGradients.AccentHorizontal))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(label, fontSize = 12.sp, color = Gray500)
                }
            }
        }
    }
}

@Composable
private fun PopularCombinations(onSeeAllClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Popüler Kombinler", style = MaterialTheme.typography.titleLarge, color = Gray800)
            Text("Tümünü Gör", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Purple600, modifier = Modifier.clickable { onSeeAllClick() })
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(4) { index ->
                Card(modifier = Modifier.width(160.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                    Column {
                        Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(Brush.verticalGradient(listOf(Purple50, Pink50))), contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Checkroom, null, tint = Purple400, modifier = Modifier.size(40.dp))
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Kombin #${index + 1}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Gray700)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, null, tint = Yellow400, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("4.8", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Gray700)
                            }
                        }
                    }
                }
            }
        }
    }
}
