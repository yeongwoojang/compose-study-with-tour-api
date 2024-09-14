package com.example.tourmanage.ui.main

import android.net.Uri
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.ui.area.AreaScreen
import com.example.tourmanage.ui.favorite.FavoriteScreen
import com.example.tourmanage.ui.festival.FestivalNavHost
import com.example.tourmanage.ui.festival.TestScreen
import com.example.tourmanage.ui.home.HomeScreen
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.google.gson.Gson
import timber.log.Timber

@Composable
fun MainNavHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomSheenOpenYn by remember { mutableStateOf(false) }
    val currentRoute = navBackStackEntry?.destination?.route?.let { currentRoute ->
        PageRoute.values().find { route -> route.route == currentRoute }
    } ?: PageRoute.HOME

    Surface {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
            topBar = {
                if (PageRoute.isBottomMenu(currentRoute)) {
                    MainTopBar(
                        currentRoute = currentRoute,
                        menuClick = {
                            bottomSheenOpenYn = true
                        }
                    )
                }
            },
            content = { padding ->
                NavHost(
                    modifier = Modifier.padding(padding),
                    navController = navController,
                    startDestination = PageRoute.HOME.route,
                    ) {
                    composable(route = PageRoute.HOME.route) {
                        HomeScreen(
                            bottomSheenOpenYn = bottomSheenOpenYn,
                            onDismissMenu = {
                                bottomSheenOpenYn = false
                            },
                            onClick = { overlayRoute, sendData ->
                                val jsonItems = Gson().toJson(sendData)
                                val uri = Uri.parse(jsonItems)
                                navController.currentBackStackEntry?.savedStateHandle?.set(key = "data", value = sendData)
                                navController.navigate(overlayRoute.route)
//                                navController.navigate("${overlayRoute.route}?uri=${uri}")
                            },
                        )
                    }

                    composable(route = PageRoute.FAVORITE.route) {
                        FavoriteScreen()
                    }

                    composable(route = PageRoute.AREA.route) {
                        AreaScreen()
                    }



                    composable(PageRoute.FESTIVAL.route) {
                        val data = remember {
                            navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<*>>("data")
                        }
                        val mainFestival = data as ArrayList<FestivalItem>
                        FestivalNavHost(mainFestival = mainFestival, onDismiss = {})
                    }
//                    composable(route = "${PageRoute.FESTIVAL.route}?uri={items}") { backStackEntry ->
//                        val itemsJson = backStackEntry.arguments?.getString("items")
//                        val decodedJsonItems = itemsJson?.let { Uri.decode(it) } // 문자열 디코딩
//                        isVisibleBar = false
//                        FestivalNavHost(json = decodedJsonItems, onDismiss = {
//                            navController.popBackStack() {}
////                            isVisibleBar = true
//                        })
//                    }
                }
            },
            bottomBar = {
                if (PageRoute.isBottomMenu(currentRoute)) {
                    MainBottomBar(
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun MainNavHostPreview() {
    TourManageTheme {

//        MainNavHost(
//            showOverlay = false
//        )
    }
}