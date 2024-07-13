package com.example.tourmanage.data

import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config

object DataProvider {
    val homeMenuList = listOf<HomeMenuItem>(
        HomeMenuItem("축제", Config.CONTENT_TYPE_ID.FESTIVAL, R.drawable.festival_menu),
        HomeMenuItem("숙소", Config.CONTENT_TYPE_ID.STAY, R.drawable.stay_menu),
        HomeMenuItem("여행코스", Config.CONTENT_TYPE_ID.TOUR_COURSE, R.drawable.walk_menu),
        HomeMenuItem("음식점", Config.CONTENT_TYPE_ID.FOOD, R.drawable.riding_menu),
        HomeMenuItem("관광지", Config.CONTENT_TYPE_ID.TOUR_SPOT, R.drawable.tour_spot_menu),
        HomeMenuItem("문화 시설", Config.CONTENT_TYPE_ID.CULTURE, R.drawable.culture_menu),
        HomeMenuItem("레포츠", Config.CONTENT_TYPE_ID.LEISURE_SPORTS, R.drawable.culture_menu),
        HomeMenuItem("쇼핑", Config.CONTENT_TYPE_ID.SHOPPING, R.drawable.culture_menu),
    )
}