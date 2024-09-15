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
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomSheenOpenYn by remember { mutableStateOf(false) }
    val currentRoute = navBackStackEntry?.destination?.route?.let { currentRoute ->
        PageRoute.entries.find { route -> route.route == currentRoute }
    } ?: PageRoute.HOME

    Surface {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
            topBar = {
                MainTopBar(
                    currentRoute = currentRoute,
                    menuClick = {
                        bottomSheenOpenYn = true
                    }
                )
            },
            bottomBar = {
                MainBottomBar(
                    currentRoute = currentRoute,
                    navController = navController
                )
            }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                bottomSheenOpenYn = bottomSheenOpenYn,
                onDisMissMenu = {
                    bottomSheenOpenYn = false
                }
            )
        }
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