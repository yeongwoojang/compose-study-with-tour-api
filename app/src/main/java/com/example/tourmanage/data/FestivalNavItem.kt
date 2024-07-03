package com.example.tourmanage.data

sealed class FestivalNavItem(
    val route: String
) {
    object Detail: FestivalNavItem("Detail")
    object Main: FestivalNavItem("Main")
}