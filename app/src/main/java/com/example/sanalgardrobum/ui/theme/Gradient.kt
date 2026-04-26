package com.example.sanalgardrobum.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Tasarımda sürekli tekrar eden gradient pattern'leri.
 * Tek bir yerden yönetilerek DRY ilkesine uyulması sağlanır.
 *
 * Kaynak: Her TSX dosyasındaki Tailwind gradient class'ları.
 */
object AppGradients {

    // ── Sayfa Arka Planı ────────────────────────────────────────────────
    // TSX: `bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50`
    // Tüm 9 sayfada kullanılıyor.
    val Background: Brush
        get() = Brush.linearGradient(
            colors = listOf(Purple50, Pink50, Blue50),
            start = Offset.Zero,
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    // ── Ana CTA Butonu ──────────────────────────────────────────────────
    // TSX: `bg-gradient-to-r from-purple-600 to-pink-600`
    // 7+ yerde kullanılıyor: CTA butonları, aktif tab, seçili filtrelerde.
    val AccentHorizontal: Brush
        get() = Brush.horizontalGradient(
            colors = listOf(Purple600, Pink600)
        )

    // ── AI / CTA Kart Arka Planı ────────────────────────────────────────
    // TSX: `bg-gradient-to-r from-purple-500 to-pink-500`
    // AI analiz kartları, profil kartı, suggestion banner.
    val AccentSoft: Brush
        get() = Brush.horizontalGradient(
            colors = listOf(Purple500, Pink500)
        )

    // ── Hero Image Overlay ──────────────────────────────────────────────
    // TSX: `bg-gradient-to-t from-black/70 via-black/20 to-transparent`
    val ImageOverlay: Brush
        get() = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.2f),
                Color.Black.copy(alpha = 0.7f)
            )
        )

    // ── Feature Card Gradient'leri ──────────────────────────────────────
    // Home sayfasındaki 4 feature kartı için ayrı gradient'ler.
    val FeaturePurplePink: Brush
        get() = Brush.linearGradient(
            colors = listOf(Purple400, Pink400),
            start = Offset.Zero,
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    val FeatureBlueCyan: Brush
        get() = Brush.linearGradient(
            colors = listOf(Blue400, Cyan400),
            start = Offset.Zero,
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    val FeatureGreenEmerald: Brush
        get() = Brush.linearGradient(
            colors = listOf(Green400, Emerald400),
            start = Offset.Zero,
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    val FeatureOrangeRed: Brush
        get() = Brush.linearGradient(
            colors = listOf(Orange400, Red400),
            start = Offset.Zero,
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )

    // ── Info Card Gradient ──────────────────────────────────────────────
    // TSX: `bg-gradient-to-r from-blue-500 to-cyan-500`
    val InfoCard: Brush
        get() = Brush.horizontalGradient(
            colors = listOf(Blue500, Cyan500)
        )

    // ── Score Breakdown Gradient'leri ────────────────────────────────────
    fun scoreGradient(color: String): Brush = when (color) {
        "purple" -> Brush.horizontalGradient(listOf(Purple400, Purple600))
        "blue" -> Brush.horizontalGradient(listOf(Blue400, Blue500))
        "pink" -> Brush.horizontalGradient(listOf(Pink400, Pink600))
        "green" -> Brush.horizontalGradient(listOf(Green400, Green600))
        "amber" -> Brush.horizontalGradient(listOf(Amber400, Amber600))
        else -> AccentHorizontal
    }

    // ── Style Badge Gradient'leri (CombinationDetail) ───────────────────
    fun styleBadgeGradient(style: String): Brush = when (style) {
        "formal" -> Brush.horizontalGradient(listOf(Blue500, Indigo600))
        "casual" -> Brush.horizontalGradient(listOf(Green400, Teal500))
        "sport" -> Brush.horizontalGradient(listOf(Orange400, Red500))
        "party" -> Brush.horizontalGradient(listOf(Pink500, Purple600))
        else -> AccentHorizontal
    }
}
