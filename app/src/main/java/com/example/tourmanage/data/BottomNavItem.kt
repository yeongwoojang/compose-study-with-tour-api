package com.example.tourmanage.data

import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config

sealed class BottomNavItem(
    val title: Int, val selectedIcon: Int, val unselectedIcon: Int, val route: String
) {
    object Home: BottomNavItem(R.string.bottom_nav_item_home, R.drawable.baseline_home_black_36, R.drawable.outline_home_black_36, Config.HOME)
    object Wish: BottomNavItem(R.string.bottom_nav_item_wish, R.drawable.baseline_favorite_black_36, R.drawable.outline_favorite_border_black_36, Config.WISH)
    object Area: BottomNavItem(R.string.bottom_nav_item_area, R.drawable.baseline_location_on_black_36, R.drawable.outline_location_on_black_36, Config.AREA)
}
