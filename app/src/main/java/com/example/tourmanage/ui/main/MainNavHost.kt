package com.example.tourmanage.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.ui.favorite.FavoriteScreen
import com.example.tourmanage.ui.home.HomeScreen
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.let { currentRoute ->
        MainRoute.values().find { route -> route.route == currentRoute }
    } ?: MainRoute.HOME

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    title = {
                        Text(
                            text = currentRoute.contentDescription,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = spoqaHanSansNeoFont,
                                fontWeight = FontWeight.Medium
                            )
                        )
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
                        HomeScreen()
                    }

                    composable(route = MainRoute.FAVORITE.route) {
                        FavoriteScreen()
                    }

                    composable(route = MainRoute.AREA.route) {
                        //TODO 어떤 화면?
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