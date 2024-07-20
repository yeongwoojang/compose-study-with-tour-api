package com.example.tourmanage.ui.stay

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.data.StayMenuItem

@Composable
fun StayNavHost(navController: NavHostController, startDestination: String, data: StayItem? = null) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(StayMenuItem.Main.route) {
            StayScreen()
        }

        composable(StayMenuItem.Detail.route) {
            if (data != null) {
                StayDetailScreen(data)
            }
        }
    }
}