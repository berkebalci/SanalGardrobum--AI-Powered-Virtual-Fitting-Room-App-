package com.example.sanalgardrobum.presentation.screens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanalgardrobum.ui.theme.Gray100
import com.example.sanalgardrobum.ui.theme.Gray700
import com.example.sanalgardrobum.ui.theme.Gray800

/**
 * Tekrar eden top bar pattern'inin composable hali.
 *
 * Tasarım: `fixed top-0 bg-white/80 backdrop-blur-md shadow-sm`
 * - Sol: geri butonu + başlık
 * - Sağ: opsiyonel aksiyon butonu
 *
 * Overlay header olduğu için Box içindeki z-index ile en üstte durmalıdır.
 *
 * @param title Başlık metni
 * @param onBackClick Geri tuşu callback'i (null ise geri butonu gösterilmez)
 * @param trailingIcon Sağ üst köşedeki opsiyonel ikon
 * @param onTrailingClick Sağ üst ikon callback'i
 */
@Composable
fun StyleAiTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    onTrailingClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp)
            .background(Color.White.copy(alpha = 0.85f))
            .padding(horizontal = 20.dp)
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sol kısım: Geri butonu + Başlık
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onBackClick != null) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Gray700,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray800
                )
            }

            // Sağ kısım: Trailing ikon veya custom content
            if (trailingContent != null) {
                trailingContent()
            } else if (trailingIcon != null && onTrailingClick != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Gray100)
                        .clickable { onTrailingClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = Gray700,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
