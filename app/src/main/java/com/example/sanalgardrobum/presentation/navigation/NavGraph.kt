package com.example.sanalgardrobum.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sanalgardrobum.presentation.screens.bodyanalysis.BodyAnalysisNavigationEvent
import com.example.sanalgardrobum.presentation.screens.bodyanalysis.BodyAnalysisScreen
import com.example.sanalgardrobum.presentation.screens.bodyanalysis.BodyAnalysisViewModel
import com.example.sanalgardrobum.presentation.screens.combinationdetail.CombinationDetailScreen
import com.example.sanalgardrobum.presentation.screens.combinationdetail.CombinationDetailViewModel
import com.example.sanalgardrobum.presentation.screens.combinations.CombinationsScreen
import com.example.sanalgardrobum.presentation.screens.combinations.CombinationsViewModel
import com.example.sanalgardrobum.presentation.screens.home.HomeScreen
import com.example.sanalgardrobum.presentation.screens.settings.SettingsScreen
import com.example.sanalgardrobum.presentation.screens.settings.SettingsViewModel
import com.example.sanalgardrobum.presentation.screens.simulationresult.SimulationResultScreen
import com.example.sanalgardrobum.presentation.screens.tryon.TryOnNavigationEvent
import com.example.sanalgardrobum.presentation.screens.tryon.TryOnScreen
import com.example.sanalgardrobum.presentation.screens.tryon.TryOnViewModel
import com.example.sanalgardrobum.presentation.screens.upload.UploadNavigationEvent
import com.example.sanalgardrobum.presentation.screens.upload.UploadScreen
import com.example.sanalgardrobum.presentation.screens.upload.UploadViewModel
import com.example.sanalgardrobum.presentation.screens.wardrobe.WardrobeScreen
import com.example.sanalgardrobum.presentation.screens.wardrobe.WardrobeViewModel

/**
 * Uygulamanın merkezi navigasyon grafiği.
 *
 * TÜM navigasyon kararları burada alınır — NavDestination composable'lar
 * yalnızca callback lambda'lar aracılığıyla "bir şey oldu" sinyali verir.
 * ViewModel'ler ise state yönetimi ve iş mantığından sorumludur.
 *
 * Mimari akış:
 * NavDestination (UI) ──callback──> NavGraph (Navigation) ──collectAsState──> ViewModel (State/Logic)
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = modifier,
        enterTransition = {
            fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = { fadeOut(tween(300)) },
        popEnterTransition = {
            fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            fadeOut(tween(300)) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        }
    ) {
        // ══════════════════════════════════════════════════════════════
        // TAB SCREENS (BottomNavBar görünür)
        // ══════════════════════════════════════════════════════════════

        composable(NavDestination.Home.route) {
            HomeScreen(
                onNavigateToUpload = { navController.navigate(NavDestination.Upload.route) },
                onNavigateToCombinations = { navController.navigate(NavDestination.Combinations.route) }
            )
        }

        composable(NavDestination.Wardrobe.route) {
            val viewModel: WardrobeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            WardrobeScreen(
                uiState = uiState,
                onCategorySelected = viewModel::onCategorySelected,
                onItemSelected = viewModel::onItemSelected,
                onItemDismissed = viewModel::onItemDismissed,
                onNavigateToUpload = { navController.navigate(NavDestination.Upload.route) },
                onNavigateToCombinations = { navController.navigate(NavDestination.Combinations.route) },
                onNavigateToTryOn = { navController.navigate(NavDestination.TryOn.route) }
            )
        }

        composable(NavDestination.Combinations.route) {
            val viewModel: CombinationsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            CombinationsScreen(
                uiState = uiState,
                onFilterSelected = viewModel::onFilterSelected,
                onCombinationClicked = { comboId ->
                    navController.navigate(NavDestination.CombinationDetail.createRoute(comboId))
                }
            )
        }

        composable(NavDestination.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            SettingsScreen(
                uiState = uiState,
                onNotificationsToggled = viewModel::onNotificationsToggled,
                onAiSuggestionsToggled = viewModel::onAiSuggestionsToggled,
                onLogoutClicked = viewModel::onLogoutClicked,
                onBackClick = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════════════════════════
        // FLOW SCREENS (Stack navigation)
        // ══════════════════════════════════════════════════════════════

        composable(NavDestination.Upload.route) {
            val viewModel: UploadViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            // ViewModel'den gelen navigation event'lerini dinle
            LaunchedEffect(Unit) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        is UploadNavigationEvent.NavigateToBodyAnalysis -> {
                            val route = NavDestination.BodyAnalysis.createRoute(
                                personImageUri = event.userPhotoUri,
                                garmentImageUri = event.clothingPhotoUri
                            )
                            navController.navigate(route) {
                                popUpTo(NavDestination.Upload.route) { inclusive = true }
                            }
                        }
                    }
                }
            }

            UploadScreen(
                uiState = uiState,
                onUserPhotoSelected = viewModel::onUserPhotoSelected,
                onUserPhotoDeleted = viewModel::onUserPhotoDeleted,
                onClothingPhotoSelected = viewModel::onClothingPhotoSelected,
                onClothingPhotoDeleted = viewModel::onClothingPhotoDeleted,
                onHeightChanged = viewModel::onHeightChanged,
                onWeightChanged = viewModel::onWeightChanged,
                onSizeSelected = viewModel::onSizeSelected,
                onAnalyzeClicked = viewModel::onAnalyzeClicked,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = NavDestination.BodyAnalysis.route,
            arguments = listOf(
                navArgument("personImageUri") { type = NavType.StringType },
                navArgument("garmentImageUri") { type = NavType.StringType }
            )
        ) {
            val viewModel: BodyAnalysisViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        is BodyAnalysisNavigationEvent.NavigateToSimulationResult ->
                            navController.navigate(NavDestination.SimulationResult.createRoute(event.imagePath)) {
                                popUpTo(NavDestination.BodyAnalysis.route) { inclusive = true }
                            }
                    }
                }
            }

            BodyAnalysisScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onRetryClick = viewModel::onRetryClicked
            )
        }

        composable(NavDestination.TryOn.route) {
            val viewModel: TryOnViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        is TryOnNavigationEvent.NavigateToSimulationResult ->
                            navController.navigate(NavDestination.SimulationResult.createRoute(event.resultImagePath))
                    }
                }
            }

            TryOnScreen(
                uiState = uiState,
                onCategorySelected = viewModel::onCategorySelected,
                onClothToggled = viewModel::onClothToggled,
                // Gerçek fotoğraf yolları UploadScreen'den gelecek.
                // Şimdilik boş string ile bağlandı; UploadScreen entegrasyonunda güncellenir.
                onSimulateClicked = { viewModel.onSimulateClicked("", "") },
                onNavigateToWardrobe = { navController.navigate(NavDestination.Wardrobe.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = NavDestination.SimulationResult.route,
            arguments = listOf(navArgument("imagePath") { type = NavType.StringType })
        ) { backStackEntry ->
            val imagePath = backStackEntry.arguments?.getString("imagePath")
            SimulationResultScreen(
                imagePath = imagePath,
                onNavigateToCombinations = { navController.navigate(NavDestination.Combinations.route) },
                onNavigateToTryOn = { navController.navigate(NavDestination.TryOn.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ══════════════════════════════════════════════════════════════
        // DETAIL SCREEN (argument ile)
        // ══════════════════════════════════════════════════════════════

        composable(
            route = NavDestination.CombinationDetail.route,
            arguments = listOf(navArgument("comboId") { type = NavType.IntType })
        ) {
            val viewModel: CombinationDetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            CombinationDetailScreen(
                uiState = uiState,
                onTabSelected = viewModel::onTabSelected,
                onSaveToggled = viewModel::onSaveToggled,
                onNavigateToSimulation = { navController.navigate(NavDestination.SimulationResult.route) },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
