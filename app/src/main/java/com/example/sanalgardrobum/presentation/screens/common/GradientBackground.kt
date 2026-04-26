package com.example.sanalgardrobum.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sanalgardrobum.ui.theme.AppGradients

/**
 * Tüm ekranlarda ortak kullanılan gradient arka plan.
 *
 * Tasarım: `bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50`
 * Tüm 9 sayfada birebir aynı pattern tekrar ediyor.
 */
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = AppGradients.Background),
        content = content
    )
}
