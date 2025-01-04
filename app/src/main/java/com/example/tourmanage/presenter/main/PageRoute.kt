package com.example.tourmanage.presenter.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class PageRoute(
    val route: String,
    val contentDescription: String,
    val icon: ImageVector? = null
) {
    HOME(route = "Home", contentDescription = "Home", Icons.Filled.Home),
    FAVORITE(route = "Favorite", contentDescription = "Favorite", Icons.Filled.Favorite),
    FESTIVAL(route = "Festival", contentDescription = "Festival"),
    STAY(route = "Stay", contentDescription = "Stay"),
    FESTIVAL_DEFAIL(route = "FestivalDetail", contentDescription = "FestivalDetail")
}