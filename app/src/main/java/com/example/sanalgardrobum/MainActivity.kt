package com.example.sanalgardrobum

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sanalgardrobum.presentation.navigation.NavDestination
import com.example.sanalgardrobum.presentation.navigation.NavGraph
import com.example.sanalgardrobum.presentation.screens.utils.BottomNavBar
import com.example.sanalgardrobum.ui.theme.SanalGardrobumTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.jar.Manifest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(!checkPermission()){
            ActivityCompat.requestPermissions(
                this,
                permission_List,
                0)
        }
        setContent {
            SanalGardrobumTheme {
                // Session kontrolü: mevcut kullanıcı varsa Home, yoksa Login
                val startDestination = if (firebaseAuth.currentUser != null) {
                    NavDestination.Home.route
                } else {
                    NavDestination.Login.route
                }
                MainScreen(startDestination = startDestination)
            }
        }
    }
    private fun checkPermission() : Boolean{
        return permission_List.all {
            ContextCompat.checkSelfPermission(
                applicationContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val permission_List= arrayOf(
            android.Manifest.permission.CAMERA,
        )
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
private fun MainScreen(startDestination: String) {
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
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        )
    }

}


