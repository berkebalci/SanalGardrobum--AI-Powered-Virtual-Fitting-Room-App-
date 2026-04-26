package com.example.sanalgardrobum.presentation.screens.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.common.*
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun UploadScreen(
    uiState: UploadUiState,
    onPhotoSelected: () -> Unit,
    onPhotoDeleted: () -> Unit,
    onHeightChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onSizeSelected: (String) -> Unit,
    onAnalyzeClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 80.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                item {
                    Text("Fotoğrafınızı Yükleyin", style = MaterialTheme.typography.headlineMedium, color = Gray800)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("En iyi sonuç için boydan, düz bir pozda çekilmiş fotoğraf kullanın", style = MaterialTheme.typography.bodyMedium, color = Gray600)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item { PhotoUploadArea(uiState.uploadedImageUri, onPhotoSelected, onPhotoDeleted); Spacer(Modifier.height(24.dp)) }
                item { TipsCard(); Spacer(Modifier.height(24.dp)) }
                item { MeasurementsForm(uiState.height, onHeightChanged, uiState.weight, onWeightChanged, uiState.selectedSize, onSizeSelected); Spacer(Modifier.height(24.dp)) }
                item {
                    GradientButton(
                        text = if (uiState.isAnalyzing) "Analiz Ediliyor..." else "Analizi Başlat",
                        onClick = onAnalyzeClicked,
                        icon = if (uiState.isAnalyzing) null else Icons.Outlined.QrCodeScanner,
                        enabled = uiState.uploadedImageUri != null,
                        isLoading = uiState.isAnalyzing
                    )
                }
            }
            StyleAiTopBar(title = "Fotoğraf Yükleme", onBackClick = onBackClick)
        }
    }
}

@Composable
private fun PhotoUploadArea(imageUri: String?, onSelectClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().height(320.dp).clickable { onSelectClick() }, shape = CardShape, elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        if (imageUri != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Purple50, Pink50))), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.CameraAlt, null, tint = Purple400, modifier = Modifier.size(60.dp)) }
                Row(modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).clip(RoundedCornerShape(50)).background(Color.White.copy(0.9f)).padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Check, null, tint = Green500, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(6.dp)); Text("Yüklendi", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Gray700) }
                Box(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp).size(40.dp).clip(CircleShape).background(Red500).clickable { onDeleteClick() }, contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Delete, "Sil", tint = Color.White, modifier = Modifier.size(20.dp)) }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(AppGradients.FeaturePurplePink), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.CameraAlt, null, tint = Color.White, modifier = Modifier.size(32.dp)) }
                Spacer(Modifier.height(16.dp)); Text("Fotoğraf Seçin", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Gray800)
                Spacer(Modifier.height(8.dp)); Text("Galeriden seçin veya\nyeni fotoğraf çekin", fontSize = 14.sp, color = Gray500, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
private fun TipsCard() {
    val tips = listOf("Düz, aydınlık bir ortamda çekin", "Vücudunuz tam görünür olsun", "Dar kıyafetler tercih edin")
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Blue50)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(Blue500), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Info, null, tint = Color.White, modifier = Modifier.size(18.dp)) }
            Spacer(Modifier.width(12.dp))
            Column {
                Text("İpuçları", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray800); Spacer(Modifier.height(8.dp))
                tips.forEach { tip -> Row(Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) { Icon(Icons.Outlined.CheckCircle, null, tint = Blue500, modifier = Modifier.size(14.dp)); Spacer(Modifier.width(8.dp)); Text(tip, fontSize = 12.sp, color = Gray600) } }
            }
        }
    }
}

@Composable
private fun MeasurementsForm(height: String, onHeightChange: (String) -> Unit, weight: String, onWeightChange: (String) -> Unit, selectedSize: String, onSizeSelected: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Vücut Ölçüleri", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Gray800)
            Text("İsteğe bağlı - Daha doğru sonuçlar için", fontSize = 12.sp, color = Gray500)
            Spacer(Modifier.height(16.dp))
            Text("Boy (cm)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700); Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = height, onValueChange = onHeightChange, placeholder = { Text("Örn: 170", color = Gray400) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Purple500, unfocusedBorderColor = Color.Transparent, unfocusedContainerColor = Gray50, focusedContainerColor = Gray50), singleLine = true)
            Spacer(Modifier.height(16.dp))
            Text("Kilo (kg)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700); Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = weight, onValueChange = onWeightChange, placeholder = { Text("Örn: 65", color = Gray400) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Purple500, unfocusedBorderColor = Color.Transparent, unfocusedContainerColor = Gray50, focusedContainerColor = Gray50), singleLine = true)
            Spacer(Modifier.height(16.dp))
            Text("Beden Tercihi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700); Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("XS", "S", "M", "L", "XL").forEach { size ->
                    val isSelected = selectedSize == size
                    Box(modifier = Modifier.weight(1f).height(48.dp).clip(RoundedCornerShape(12.dp)).then(if (isSelected) Modifier.background(AppGradients.AccentSoft) else Modifier.background(Gray50)).clickable { onSizeSelected(size) }, contentAlignment = Alignment.Center) {
                        Text(size, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = if (isSelected) Color.White else Gray600)
                    }
                }
            }
        }
    }
}
