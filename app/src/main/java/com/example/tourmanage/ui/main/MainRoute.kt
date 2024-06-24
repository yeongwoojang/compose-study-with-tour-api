package com.example.tourmanage.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainRoute(
    val route: String,
    val contentDescription: String,
    val icon: ImageVector
) {
    HOME(route = "Home", contentDescription = "Home", Icons.Filled.Home),
    FAVORITE(route = "Favorite", contentDescription = "Favorite", Icons.Filled.Favorite),
    AREA(route = "Area", contentDescription = "Area", Icons.Filled.LocationOn)
}