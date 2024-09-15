package com.example.tourmanage.ui.main

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
    AREA(route = "Area", contentDescription = "Area", Icons.Filled.LocationOn),
    FESTIVAL(route = "Festival", contentDescription = "Festival", null),
    STAY(route = "Stay", contentDescription = "Stay", null);
}