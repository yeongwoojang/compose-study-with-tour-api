package com.example.tourmanage.data

sealed class StayMenuItem(
    val route: String
) {
    object Main: StayMenuItem("Main")
    object Detail: StayMenuItem("Detail")
}