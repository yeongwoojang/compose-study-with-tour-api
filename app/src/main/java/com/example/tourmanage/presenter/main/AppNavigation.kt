package com.example.tourmanage.presenter.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.presenter.area.AreaScreen
import com.example.tourmanage.presenter.favorite.FavoriteScreen
import com.example.tourmanage.presenter.festival.FestivalDetailScreen
import com.example.tourmanage.presenter.home.HomeScreen
import com.example.tourmanage.presenter.home.OverlayRoute
import com.example.tourmanage.presenter.stay.StayScreen
import timber.log.Timber

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    bottomSheenOpenYn: Boolean,
    onDisMissMenu: () -> Unit
) {
    NavHost(navController = navController, startDestination = "bottom_menu") {
        navigation(startDestination = PageRoute.HOME.route, route = "bottom_menu") {
            composable(route = PageRoute.HOME.route) {
                HomeScreen(
                    modifier = modifier,
                    bottomSheenOpenYn = bottomSheenOpenYn,
                    onDismissMenu = onDisMissMenu,
                    onClick = { overlayRoute, posterItem ->
                        if (overlayRoute == OverlayRoute.FESTIVAL || overlayRoute == OverlayRoute.STAY) {
                            navController.apply {
                                currentBackStackEntry?.savedStateHandle?.set(
                                    key = "posterItem",
                                    value = posterItem
                                )

                                navigate(overlayRoute.route)
                            }
                        }
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

        navigation(startDestination = OverlayRoute.FESTIVAL.route, route = "detail_page") {
            composable(
                OverlayRoute.FESTIVAL.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )
                },
            ) {
                val posterItem = remember {
                    navController.previousBackStackEntry?.savedStateHandle?.get<PosterItem>("posterItem")
                }
                FestivalDetailScreen(
                    posterItem = posterItem
                )
            }

            composable(PageRoute.STAY.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )
                }
            ) { backStackEntry ->
                val posterItem = remember {
                    navController.previousBackStackEntry?.savedStateHandle?.get<PosterItem>("posterItem")
                }
                StayScreen(modifier = modifier, posterItem = posterItem, close = {
                    navController.popBackStack()
                })

            }
        }
    }

}

val bottomRoutes = setOf(PageRoute.HOME, PageRoute.FAVORITE, PageRoute.AREA)