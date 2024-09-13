package com.example.tourmanage.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.ui.festival.FestivalNavHost
import com.example.tourmanage.ui.home.OverlayRoute
import com.google.gson.Gson

@Composable
fun OverlayNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = OverlayRoute.FESTIVAL.route,
    ) {
        composable("${OverlayRoute.FESTIVAL.route}/{festivalList}",
            arguments = listOf(navArgument("festivalList") { type = NavType.StringType })
        ) { backStackEntry ->
            val festivalJson = backStackEntry.arguments?.getString("festivalList")
            FestivalNavHost(festivalJson)
//            DetailsScreen(navController, festivalJson)

//            showFestival()
        }
    }
}

//fun NavHostController.navigateToFestival(mainFestival: String) {
//    this.navigate("${OverlayRoute.FESTIVAL.route}}/$mainFestival")
//}
//
//fun NavGraphBuilder.showFestival(navigateToMain: () -> Unit) {
//    composable("${OverlayRoute.FESTIVAL.route}/{mainFestival}",
//        arguments = listOf(
//            navArgument("mainFestival") { type = NavType.StringType },
//        )
//    ) { navBackStackEntry->
//        val mainFestivalJson = navBackStackEntry.arguments?.getString("mainFestival")
//        val teamData = Gson().fromJson(mainFestivalJson, ArrayList::class.java)
//
//    }
//}