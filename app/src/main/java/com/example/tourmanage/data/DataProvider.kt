package com.example.tourmanage.data

import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config

object DataProvider {
    val homeMenuList = listOf<HomeMenuItem>(
        HomeMenuItem("축제", Config.HOME_MENU_TYPE.FESTIVAL, R.drawable.festival_menu),
        HomeMenuItem("숙소", Config.HOME_MENU_TYPE.STAY, R.drawable.stay_menu),
        HomeMenuItem("여행코스", Config.HOME_MENU_TYPE.COURSE, R.drawable.walk_menu),
        HomeMenuItem("음식점", Config.HOME_MENU_TYPE.FOOD, R.drawable.riding_menu),
        HomeMenuItem("관광지", Config.HOME_MENU_TYPE.TOUR_SPOT, R.drawable.tour_spot_menu),
        HomeMenuItem("문화 시설", Config.HOME_MENU_TYPE.CULTURE, R.drawable.culture_menu),
        HomeMenuItem("레포츠", Config.HOME_MENU_TYPE.LEISURE, R.drawable.culture_menu),
        HomeMenuItem("쇼핑", Config.HOME_MENU_TYPE.SHOPPING, R.drawable.culture_menu),
    )
}