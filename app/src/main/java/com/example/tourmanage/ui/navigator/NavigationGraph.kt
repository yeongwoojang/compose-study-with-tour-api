package com.example.tourmanage.ui.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourmanage.data.BottomNavItem
import com.example.tourmanage.ui.area.AreaWidget
import com.example.tourmanage.ui.home.HomeWidget
import com.example.tourmanage.ui.wish.WishWidget

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeWidget()
        }
        composable(BottomNavItem.Wish.route) {
            WishWidget()
        }
        composable(BottomNavItem.Area.route) {
            AreaWidget()
        }

    }
}