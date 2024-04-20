package com.example.tourmanage.data

import com.example.tourmanage.common.value.Config

data class HomeMenuItem(
    var title: String = "",
    var type: Config.HOME_MENU_TYPE,
    var image: Int
)
