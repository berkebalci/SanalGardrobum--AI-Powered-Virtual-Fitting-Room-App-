package com.example.sanalgardrobum.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Tailwind → Compose Shape eşleşmesi:
 *
 * | Tailwind Class   | dp Değer | Kullanım Yeri                      |
 * |------------------|----------|------------------------------------|
 * | rounded-full     | 50%      | Avatar, badge, toggle, icon bg     |
 * | rounded-3xl      | 24.dp    | Card container, modal, bottom nav  |
 * | rounded-2xl      | 16.dp    | Button, input, inner card          |
 * | rounded-xl       | 12.dp    | Icon bg, tag, small chip           |
 * | rounded-lg       | 8.dp     | Small elements                     |
 */
val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),   // rounded-lg
    small = RoundedCornerShape(12.dp),       // rounded-xl
    medium = RoundedCornerShape(16.dp),      // rounded-2xl
    large = RoundedCornerShape(24.dp),       // rounded-3xl
    extraLarge = RoundedCornerShape(28.dp)   // rounded-3xl+ (modal)
)

// Ek sabit shape'ler — Shapes sınıfına sığmayan veya sık tekrar eden
val CardShape = RoundedCornerShape(24.dp)
val ButtonShape = RoundedCornerShape(16.dp)
val IconBgShape = RoundedCornerShape(12.dp)
val ChipShape = RoundedCornerShape(50)       // rounded-full → Capsule
val BottomSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
