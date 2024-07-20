package com.example.tourmanage.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.FestivalMainActivity
import com.example.tourmanage.ui.area.AreaScreen
import com.example.tourmanage.ui.favorite.FavoriteScreen
import com.example.tourmanage.ui.home.HomeRoute
import com.example.tourmanage.ui.home.HomeScreen
import com.example.tourmanage.ui.stay.StayMainActivity

@Composable
fun MainNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomSheenOpenYn by remember { mutableStateOf(false) }

    val currentRoute = navBackStackEntry?.destination?.route?.let { currentRoute ->
        MainRoute.values().find { route -> route.route == currentRoute }
    } ?: MainRoute.HOME

    Surface {
        Scaffold(
            topBar = {
                MainTopBar(
                    currentRoute = currentRoute,
                    menuClick = {
                        bottomSheenOpenYn = true
                    }
                )
            },
            content = { padding ->
                NavHost(
                    modifier = Modifier.padding(padding),
                    navController = navController,
                    startDestination = MainRoute.HOME.route
                ) {
                    composable(route = MainRoute.HOME.route) {
                        HomeScreen(
                            bottomSheenOpenYn = bottomSheenOpenYn,
                            onDismissMenu = {
                                bottomSheenOpenYn = false
                            },
                            onClick = { homeRoute, sendData
                                ->
                                when (homeRoute) {
                                    HomeRoute.FESTIVAL -> UiController
                                        .addActivity(
                                            context = context,
                                            targetActivity = FestivalMainActivity::class,
                                            data = IntentData(
                                                mapOf(Config.PASS_DATA.DATA.value to sendData)
                                            )
                                        )
                                    HomeRoute.STAY -> UiController
                                        .addActivity(
                                            context = context,
                                            targetActivity = StayMainActivity::class,
                                            data = if (sendData != null) IntentData(
                                                mapOf(Config.PASS_DATA.DATA.value to sendData)
                                            ) else null
                                        )
                                }

                            },
                        )
                    }

                    composable(route = MainRoute.FAVORITE.route) {
                        FavoriteScreen()
                    }

                    composable(route = MainRoute.AREA.route) {
                        AreaScreen()
                    }
                }
            },
            bottomBar = {
                MainBottomBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        )
    }
}


@Preview
@Composable
fun MainNavHostPreview() {

}