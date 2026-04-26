package com.example.sanalgardrobum.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Tasarıma göre özelleştirilmiş Material3 renk şeması.
 *
 * Dikkat: Dynamic Color (Android 12+) devre dışı bırakıldı çünkü
 * tasarımın kendi Purple-Pink paleti var ve cihaz duvar kağıdına
 * göre değişmemesi gerekiyor.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple600,
    onPrimary = Color.White,
    primaryContainer = Purple50,
    onPrimaryContainer = Purple700,

    secondary = Pink600,
    onSecondary = Color.White,
    secondaryContainer = Pink50,
    onSecondaryContainer = Pink600,

    tertiary = Blue500,
    onTertiary = Color.White,
    tertiaryContainer = Blue50,
    onTertiaryContainer = Blue500,

    background = Color.White, // Gradient arka plan ayrıca uygulanıyor
    onBackground = Gray800,

    surface = Color.White,
    onSurface = Gray800,
    surfaceVariant = Gray50,
    onSurfaceVariant = Gray600,

    outline = Gray200,
    outlineVariant = Gray100,

    error = Red500,
    onError = Color.White,
    errorContainer = Red50,
    onErrorContainer = Red600,

    inverseSurface = Gray900,
    inverseOnSurface = Gray50,
    inversePrimary = Purple400
)

// Dark scheme'de kullanılan ek renkler
private val Purple900 = Color(0xFF581C87)

private val DarkColorScheme = darkColorScheme(
    primary = Purple400,
    onPrimary = Purple900,
    primaryContainer = Purple700,
    onPrimaryContainer = Purple100,

    secondary = Pink400,
    onSecondary = Color.Black,
    secondaryContainer = Pink600,
    onSecondaryContainer = Pink50,

    tertiary = Blue400,
    onTertiary = Color.Black,
    tertiaryContainer = Blue500,
    onTertiaryContainer = Blue50,

    background = Gray900,
    onBackground = Gray50,

    surface = Gray900,
    onSurface = Gray50,
    surfaceVariant = Gray800,
    onSurfaceVariant = Gray400,

    outline = Gray700,
    outlineVariant = Gray800,

    error = Red400,
    onError = Color.Black,
    errorContainer = Red600,
    onErrorContainer = Red50
)

@Composable
fun SanalGardrobumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}