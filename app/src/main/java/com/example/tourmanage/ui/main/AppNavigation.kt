package com.example.tourmanage.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.ui.area.AreaScreen
import com.example.tourmanage.ui.favorite.FavoriteScreen
import com.example.tourmanage.ui.festival.FestivalNavHost
import com.example.tourmanage.ui.home.HomeScreen
import com.example.tourmanage.ui.home.OverlayRoute
import com.google.gson.Gson

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
    bottomSheenOpenYn: Boolean,
    onDisMissMenu: () -> Unit
) {
    NavHost(navController = navController, modifier = modifier, startDestination = "bottom_menu") {
        navigation(startDestination = PageRoute.HOME.route, route = "bottom_menu") {
            composable(route = PageRoute.HOME.route) {
                HomeScreen(
                    bottomSheenOpenYn = bottomSheenOpenYn,
                    onDismissMenu = onDisMissMenu,
                    onClick = { overlayRoute, sendData ->
                        val jsonItems = Gson().toJson(sendData)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "data",
                            value = sendData
                        )
                        navController.navigate(overlayRoute.route)
                    },
                )
            }

            composable(route = PageRoute.FAVORITE.route) {
                FavoriteScreen()
            }

            composable(route = PageRoute.AREA.route) {
                AreaScreen()
            }
        }

        navigation(startDestination = OverlayRoute.FESTIVAL.route, route = "festival_page") {
            composable(
                OverlayRoute.FESTIVAL.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(700)
                    )
                },
            ) {
                val data = remember {
                    navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<*>>("data")
                }
                val mainFestival = data as ArrayList<FestivalItem>
                FestivalNavHost(
                    mainFestival = mainFestival,
                    onDismiss = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

}

val bottomRoutes = setOf(PageRoute.HOME, PageRoute.FAVORITE, PageRoute.AREA)

val bottomNavigationItems = listOf(
    PageRoute.HOME, PageRoute.FAVORITE, PageRoute.AREA
)
