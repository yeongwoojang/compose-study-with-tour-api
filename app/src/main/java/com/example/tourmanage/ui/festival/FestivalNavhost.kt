package com.example.tourmanage.ui.festival

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.data.FestivalNavItem
import com.example.tourmanage.ui.main.PageRoute
import com.example.tourmanage.viewmodel.FestivalViewModel
import timber.log.Timber

@Composable
fun FestivalNavHost(
    viewModel: FestivalViewModel = hiltViewModel(),
    mainFestival: ArrayList<FestivalItem>,
    onDismiss: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = FestivalNavItem.Main.route
    ) {
        composable(FestivalNavItem.Main.route) {
            FestivalMainScreen(
                viewModel = viewModel,
                mainFestival = mainFestival,
                choiceFestival = {
                    navController.navigate("${FestivalNavItem.Detail.route}/$it")
                },
                onDismissFestivalPage = onDismiss
            )
        }

        composable(
            route = "${FestivalNavItem.Detail.route}/{contentId}",
            arguments = listOf(
                navArgument("contentId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId")
            LaunchedEffect(Unit) {
                viewModel.requestFestivalDetail(contentId)
            }
            FestivalDetailScreen(
                viewModel = viewModel,
                festivalDetailState = viewModel.festivalDetailInfo.collectAsStateWithLifecycle()
            )
        }
    }
}