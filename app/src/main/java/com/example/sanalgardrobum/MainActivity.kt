package com.example.sanalgardrobum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sanalgardrobum.presentation.navigation.NavDestination
import com.example.sanalgardrobum.presentation.navigation.NavGraph
import com.example.sanalgardrobum.presentation.screens.common.BottomNavBar
import com.example.sanalgardrobum.ui.theme.SanalGardrobumTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanalGardrobumTheme {
                MainScreen()
            }
        }
    }
}

// ── Bottom bar'ın gösterildiği route'lar ─────────────────────────────────────
private val bottomBarRoutes = setOf(
    NavDestination.Home.route,
    NavDestination.Wardrobe.route,
    NavDestination.Combinations.route,
    NavDestination.Settings.route
)

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}