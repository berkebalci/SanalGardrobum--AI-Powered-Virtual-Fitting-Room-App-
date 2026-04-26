package com.example.sanalgardrobum.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.presentation.screens.common.GradientBackground
import com.example.sanalgardrobum.presentation.screens.common.StyleAiTopBar
import com.example.sanalgardrobum.ui.theme.*

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onNotificationsToggled: (Boolean) -> Unit,
    onAiSuggestionsToggled: (Boolean) -> Unit,
    onLogoutClicked: () -> Unit,
    onBackClick: () -> Unit
) {
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 80.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)) {
                item {
                    Card(Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.Transparent), elevation = CardDefaults.cardElevation(4.dp)) {
                        Column(Modifier.background(AppGradients.AccentSoft).padding(24.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(64.dp).clip(CircleShape).background(Color.White.copy(0.2f)), contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Person, null, tint = Color.White, modifier = Modifier.size(32.dp)) }
                                Spacer(Modifier.width(16.dp)); Column { Text("Kullanıcı Adı", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White); Text("user@example.com", fontSize = 14.sp, color = Color.White.copy(0.8f)) }
                            }
                            Spacer(Modifier.height(16.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                listOf("24" to "Kıyafet", "12" to "Kombin", "48" to "Deneme").forEach { (v, l) -> Card(Modifier.weight(1f), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f))) { Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(v, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White); Text(l, fontSize = 12.sp, color = Color.White.copy(0.8f)) } } }
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item { SettingsSection("HESAP", listOf(SItem(Icons.Outlined.Person, "Profil Bilgileri"), SItem(Icons.Outlined.Person, "Vücut Ölçüleri"), SItem(Icons.Outlined.Lock, "Gizlilik"))); Spacer(Modifier.height(24.dp)) }
                item { SettingsSection("TERCİHLER", listOf(SItem(Icons.Outlined.Palette, "Stil Tercihleri"), SItem(Icons.Outlined.Checkroom, "Beden Tercihleri"), SItem(Icons.Outlined.Contrast, "Renk Paleti"))); Spacer(Modifier.height(24.dp)) }
                item {
                    Column {
                        Text("UYGULAMA", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Gray500, letterSpacing = 1.sp); Spacer(Modifier.height(12.dp))
                        Card(Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                            Column {
                                ToggleRow(Icons.Outlined.Notifications, "Bildirimler", uiState.notifications, onNotificationsToggled)
                                HorizontalDivider(Modifier.padding(horizontal = 20.dp), color = Gray100)
                                ToggleRow(Icons.Outlined.SmartToy, "Yapay Zeka Önerileri", uiState.aiSuggestions, onAiSuggestionsToggled)
                                HorizontalDivider(Modifier.padding(horizontal = 20.dp), color = Gray100)
                                SettingsRow(SItem(Icons.Outlined.Language, "Dil"))
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item {
                    Card(Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) {
                        Column(Modifier.padding(20.dp)) {
                            Text("Hakkında", fontWeight = FontWeight.Bold, color = Gray800); Spacer(Modifier.height(16.dp))
                            AboutRow("Versiyon", "1.0.0"); AboutRow("Gizlilik Politikası"); AboutRow("Kullanım Koşulları")
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
                item { Box(Modifier.fillMaxWidth().height(52.dp).clip(RoundedCornerShape(16.dp)).background(Red50).clickable { onLogoutClicked() }, contentAlignment = Alignment.Center) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.AutoMirrored.Filled.Logout, null, tint = Red600, modifier = Modifier.size(20.dp)); Spacer(Modifier.width(8.dp)); Text("Çıkış Yap", fontWeight = FontWeight.SemiBold, color = Red600) } } }
            }
            StyleAiTopBar(title = "Ayarlar", onBackClick = onBackClick)
        }
    }
}

private data class SItem(val icon: ImageVector, val label: String)

@Composable
private fun SettingsSection(title: String, items: List<SItem>) {
    Column { Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Gray500, letterSpacing = 1.sp); Spacer(Modifier.height(12.dp))
        Card(Modifier.fillMaxWidth(), shape = CardShape, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(1.dp)) { Column { items.forEachIndexed { i, item -> SettingsRow(item); if (i < items.lastIndex) HorizontalDivider(Modifier.padding(horizontal = 20.dp), color = Gray100) } } }
    }
}

@Composable
private fun SettingsRow(item: SItem) {
    Row(Modifier.fillMaxWidth().clickable { }.padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Purple50), contentAlignment = Alignment.Center) { Icon(item.icon, null, tint = Purple600, modifier = Modifier.size(18.dp)) }; Spacer(Modifier.width(12.dp)); Text(item.label, fontWeight = FontWeight.SemiBold, color = Gray800) }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Gray400, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun ToggleRow(icon: ImageVector, label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Purple50), contentAlignment = Alignment.Center) { Icon(icon, null, tint = Purple600, modifier = Modifier.size(18.dp)) }; Spacer(Modifier.width(12.dp)); Text(label, fontWeight = FontWeight.SemiBold, color = Gray800) }
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Purple600, uncheckedThumbColor = Color.White, uncheckedTrackColor = Gray300))
    }
}

@Composable
private fun AboutRow(label: String, value: String? = null) {
    Row(Modifier.fillMaxWidth().clickable { }.padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontSize = 14.sp, color = Gray600)
        if (value != null) Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray800) else Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Gray400, modifier = Modifier.size(18.dp))
    }
}
