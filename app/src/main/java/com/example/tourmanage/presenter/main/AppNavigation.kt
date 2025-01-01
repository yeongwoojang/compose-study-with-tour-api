package com.example.tourmanage.presenter.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tourmanage.presenter.area.AreaScreen
import com.example.tourmanage.presenter.favorite.FavoriteScreen
import com.example.tourmanage.presenter.festival.FestivalDetailScreen
import com.example.tourmanage.presenter.home.HomeScreen
import com.example.tourmanage.presenter.home.OverlayRoute
import com.example.tourmanage.presenter.stay.StayScreen

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
                    onClick = { overlayRoute, contentId ->
                        navController.apply {
                            currentBackStackEntry?.savedStateHandle?.set(
                                key = "contentId",
                                value = contentId
                            )
                            navigate(overlayRoute.route)
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
                val contentId = remember {
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("contentId")
                }
                FestivalDetailScreen(
                    contentId = contentId?: ""
                )
            }

            composable(PageRoute.STAY.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )
                }
            ) { backStackEntry ->
                val contentId = remember {
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("contentId")
                }
                StayScreen(modifier = modifier, contentId = contentId, close = {
                    navController.popBackStack()
                })

            }
        }
    }

}

val bottomRoutes = setOf(PageRoute.HOME, PageRoute.FAVORITE, PageRoute.AREA)