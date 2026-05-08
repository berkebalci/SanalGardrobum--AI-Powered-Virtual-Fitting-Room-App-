package com.example.sanalgardrobum.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Uygulamadaki tüm ekranların route tanımları.
 *
 * sealed class kullanılarak type-safe navigation sağlanır.
 * NavGraph.kt bu route'ları kullanarak ekranları bağlar.
 */
sealed class NavDestination(val route: String) {
    data object Home : NavDestination("home")
    data object Upload : NavDestination("upload")
    data object BodyAnalysis : NavDestination("body_analysis/{personImageUri}/{garmentImageUri}") {
        fun createRoute(personImageUri: String, garmentImageUri: String): String {
            val encodedPerson = URLEncoder.encode(personImageUri, StandardCharsets.UTF_8.toString())
            val encodedGarment = URLEncoder.encode(garmentImageUri, StandardCharsets.UTF_8.toString())
            return "body_analysis/$encodedPerson/$encodedGarment"
        }
    }
    data object TryOn : NavDestination("try_on")
    data object SimulationResult : NavDestination("simulation_result/{imagePath}") {
        fun createRoute(imagePath: String): String {
            val encodedPath = java.net.URLEncoder.encode(imagePath, java.nio.charset.StandardCharsets.UTF_8.toString())
            return "simulation_result/$encodedPath"
        }
    }
    data object Wardrobe : NavDestination("wardrobe")
    data object Combinations : NavDestination("combinations")
    data object CombinationDetail : NavDestination("combination_detail/{comboId}") {
        fun createRoute(comboId: Int): String = "combination_detail/$comboId"
    }
    data object Settings : NavDestination("settings")
}
