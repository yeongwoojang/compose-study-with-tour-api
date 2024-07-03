package com.example.tourmanage.ui.festival

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.data.FestivalNavItem
import com.example.tourmanage.viewmodel.FestivalViewModel

@Composable
fun FestivalNavHost(navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    val viewModel: FestivalViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = FestivalNavItem.Main.route
    ) {
        composable(FestivalNavItem.Main.route) {
            FestivalMainWidget(
                viewModel,
                mainFestival = mainFestival,
                moveToDetail = {
                    navController.navigate(it)
                },
                choiceFestival = viewModel::choiceFestival
            )
        }

        composable(
            route = "${FestivalNavItem.Detail.route}/{item}",
            arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                }
            )
        ) { backstackentry ->
            FestivalDetail(
                viewModel,
                navController,
                backstackentry
            )
        }
    }
}