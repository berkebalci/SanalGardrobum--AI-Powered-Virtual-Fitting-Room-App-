package com.example.sanalgardrobum.presentation.screens.upload

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.sanalgardrobum.presentation.screens.utils.*
import com.example.sanalgardrobum.ui.theme.*
import java.io.File

fun Context.createTempImageUri(): Uri {
    val imageFile = File(cacheDir, "images").apply { mkdirs() }
    val file = File.createTempFile("JPEG_${System.currentTimeMillis()}_", ".jpg", imageFile)
    return FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    uiState: UploadUiState,
    onUserPhotoSelected: (String) -> Unit,
    onUserPhotoDeleted: () -> Unit,
    onClothingPhotoSelected: (String) -> Unit,
    onClothingPhotoDeleted: () -> Unit,
    onHeightChanged: (String) -> Unit,
    onWeightChanged: (String) -> Unit,
    onSizeSelected: (String) -> Unit,
    onAnalyzeClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var activeStep by remember { mutableStateOf(0) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && tempPhotoUri != null) {
            if (activeStep == 1) onUserPhotoSelected(tempPhotoUri.toString())
            else if (activeStep == 2) onClothingPhotoSelected(tempPhotoUri.toString())
        }
    }

    val pickMediaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            if (activeStep == 1) onUserPhotoSelected(it.toString())
            else if (activeStep == 2) onClothingPhotoSelected(it.toString())
        }
    }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 88.dp, bottom = 32.dp, start = 20.dp, end = 20.dp)
            ) {
                // ── Başlık ──────────────────────────────────────────────
                item {
                    Text(
                        "Fotoğrafları Yükleyin",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Gray800
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Kıyafeti üzerinde sanal olarak denemek için iki fotoğraf gerekli",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray500
                    )
                    Spacer(Modifier.height(24.dp))
                }

                // ── Adım 1: Kullanıcı fotoğrafı ─────────────────────────
                item {
                    StepPhotoUploadCard(
                        stepNumber = 1,
                        stepTitle = "Senin Fotoğrafın",
                        imageUri = uiState.userPhotoUri,
                        emptyIcon = Icons.Outlined.Person,
                        emptyLabel = "Fotoğrafını Seç",
                        emptyDescription = "Boydan, düz pozda çekilmiş\nfotoğraf kullanın",
                        onSelectClick = { 
                            activeStep = 1
                            showBottomSheet = true
                        },
                        onDeleteClick = onUserPhotoDeleted
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // ── Adım 2: Kıyafet fotoğrafı ────────────────────────────
                item {
                    StepPhotoUploadCard(
                        stepNumber = 2,
                        stepTitle = "Kıyafet Fotoğrafı",
                        imageUri = uiState.clothingPhotoUri,
                        emptyIcon = Icons.Outlined.Checkroom,
                        emptyLabel = "Kıyafet Fotoğrafı Seç",
                        emptyDescription = "Ürünü düz, tek renk arka planla\nnet gösteren fotoğraf",
                        onSelectClick = { 
                            activeStep = 2
                            showBottomSheet = true
                        },
                        onDeleteClick = onClothingPhotoDeleted
                    )
                    Spacer(Modifier.height(20.dp))
                }

                // ── İpuçları kartı ────────────────────────────────────────
                item {
                    TipsCard()
                    Spacer(Modifier.height(20.dp))
                }

                // ── Vücut ölçüleri formu ──────────────────────────────────
                item {
                    MeasurementsForm(
                        height = uiState.height,
                        onHeightChange = onHeightChanged,
                        weight = uiState.weight,
                        onWeightChange = onWeightChanged,
                        selectedSize = uiState.selectedSize,
                        onSizeSelected = onSizeSelected
                    )
                    Spacer(Modifier.height(28.dp))
                }

                // ── Analiz butonu ─────────────────────────────────────────
                item {
                    GradientButton(
                        text = if (uiState.isAnalyzing) "Analiz Ediliyor..." else "Analizi Başlat",
                        onClick = onAnalyzeClicked,
                        icon = if (uiState.isAnalyzing) null else Icons.Outlined.AutoAwesome,
                        enabled = uiState.canAnalyze,
                        isLoading = uiState.isAnalyzing
                    )
                }
            }

            StyleAiTopBar(title = "Sanal Dene", onBackClick = onBackClick)
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        "Fotoğraf Yükle",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Gray800
                    )
                    Spacer(Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PickerOption(
                            icon = Icons.Outlined.PhotoCamera,
                            label = "Kamera",
                            onClick = {
                                showBottomSheet = false
                                tempPhotoUri = context.createTempImageUri()
                                takePictureLauncher.launch(tempPhotoUri!!)
                            }
                        )
                        PickerOption(
                            icon = Icons.Outlined.PhotoLibrary,
                            label = "Galeri",
                            onClick = {
                                showBottomSheet = false
                                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        )
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun PickerOption(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Blue50),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Blue500, modifier = Modifier.size(28.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text(label, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = Gray700)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Numaralı adım fotoğraf yükleme kartı
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun StepPhotoUploadCard(
    stepNumber: Int,
    stepTitle: String,
    imageUri: String?,
    emptyIcon: ImageVector,
    emptyLabel: String,
    emptyDescription: String,
    onSelectClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column {
        // Adım başlığı (numara + başlık)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(AppGradients.AccentSoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = stepTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Gray800
            )
        }

        Spacer(Modifier.height(10.dp))

        // Fotoğraf alanı
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { onSelectClick() },
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            AnimatedVisibility(
                visible = imageUri != null,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(200))
            ) {
                // Yüklendi hali
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Yüklendi rozeti
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.92f))
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                            tint = Green500,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text("Yüklendi", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Gray700)
                    }
                    // Sil butonu
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Red500)
                            .clickable { onDeleteClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Delete, "Sil", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }

            AnimatedVisibility(
                visible = imageUri == null,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(200))
            ) {
                // Boş hal
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(listOf(Purple100, Pink50))
                            )
                            .border(2.dp, Brush.linearGradient(listOf(Purple300, Pink300)), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(emptyIcon, contentDescription = null, tint = Purple500, modifier = Modifier.size(30.dp))
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(emptyLabel, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Gray800)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        emptyDescription,
                        fontSize = 12.sp,
                        color = Gray500,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// İpuçları kartı
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun TipsCard() {
    val tips = listOf(
        "Düz, aydınlık bir ortamda çekin",
        "Kıyafet tek renk arka planda net görünsün",
        "Dar kıyafetlerle daha doğru sonuç alınır"
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Blue50)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Blue500),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Lightbulb, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text("İpuçları", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray800)
                Spacer(Modifier.height(8.dp))
                tips.forEach { tip ->
                    Row(Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = Blue500, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(tip, fontSize = 12.sp, color = Gray600, lineHeight = 16.sp)
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Vücut ölçüleri formu
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MeasurementsForm(
    height: String,
    onHeightChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    selectedSize: String,
    onSizeSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Vücut Ölçüleri", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Gray800)
            Text("İsteğe bağlı · Daha doğru sonuçlar için", fontSize = 12.sp, color = Gray500)
            Spacer(Modifier.height(18.dp))

            // Boy
            Text("Boy (cm)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                placeholder = { Text("Örn: 170", color = Gray400) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Purple500,
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Gray50,
                    focusedContainerColor = Gray50
                ),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))

            // Kilo
            Text("Kilo (kg)", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                placeholder = { Text("Örn: 65", color = Gray400) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Purple500,
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Gray50,
                    focusedContainerColor = Gray50
                ),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))

            // Beden
            Text("Beden Tercihi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Gray700)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("XS", "S", "M", "L", "XL").forEach { size ->
                    val isSelected = selectedSize == size
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .then(
                                if (isSelected)
                                    Modifier.background(AppGradients.AccentSoft)
                                else
                                    Modifier
                                        .background(Gray50)
                                        .border(1.dp, Gray100, RoundedCornerShape(12.dp))
                            )
                            .clickable { onSizeSelected(size) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            size,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = if (isSelected) Color.White else Gray600
                        )
                    }
                }
            }
        }
    }
}
