package com.example.sanalgardrobum.presentation.screens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import com.example.sanalgardrobum.ui.theme.AppGradients
import com.example.sanalgardrobum.ui.theme.ChipShape
import com.example.sanalgardrobum.ui.theme.Gray600

/**
 * Kategorileri yatay olarak filtreleyen scrollable chip row.
 *
 * Tasarım: `flex gap-2 overflow-x-auto` ile `rounded-full` chip'ler.
 * TryOn ve Wardrobe ekranlarında aynı visual pattern.
 *
 * @param categories Filtreleme kategorileri
 * @param selectedCategoryId Seçili kategori ID'si
 * @param onCategorySelected Kategori seçim callback'i
 */
data class FilterCategory(
    val id: String,
    val label: String,
    val icon: ImageVector? = null
)

@Composable
fun CategoryFilterRow(
    categories: List<FilterCategory>,
    selectedCategoryId: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category.id == selectedCategoryId

            Row(
                modifier = Modifier
                    .then(
                        if (isSelected) Modifier.shadow(4.dp, ChipShape)
                        else Modifier
                    )
                    .clip(ChipShape)
                    .background(
                        brush = if (isSelected) {
                            AppGradients.AccentHorizontal
                        } else {
                            androidx.compose.ui.graphics.Brush.horizontalGradient(
                                listOf(Color.White, Color.White)
                            )
                        }
                    )
                    .clickable { onCategorySelected(category.id) }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (category.icon != null) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isSelected) Color.White else Gray600
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = category.label,
                    color = if (isSelected) Color.White else Gray600,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
