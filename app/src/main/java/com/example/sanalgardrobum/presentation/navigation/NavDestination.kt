package com.example.sanalgardrobum.presentation.navigation

/**
 * Uygulamadaki tüm ekranların route tanımları.
 *
 * sealed class kullanılarak type-safe navigation sağlanır.
 * NavGraph.kt bu route'ları kullanarak ekranları bağlar.
 */
sealed class NavDestination(val route: String) {
    data object Home : NavDestination("home")
    data object Upload : NavDestination("upload")
    data object BodyAnalysis : NavDestination("body_analysis")
    data object TryOn : NavDestination("try_on")
    data object SimulationResult : NavDestination("simulation_result")
    data object Wardrobe : NavDestination("wardrobe")
    data object Combinations : NavDestination("combinations")
    data object CombinationDetail : NavDestination("combination_detail/{comboId}") {
        fun createRoute(comboId: Int): String = "combination_detail/$comboId"
    }
    data object Settings : NavDestination("settings")
}
