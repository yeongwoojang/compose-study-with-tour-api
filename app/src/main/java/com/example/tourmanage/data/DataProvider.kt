package com.example.tourmanage.data

import com.example.tourmanage.common.value.Config

object DataProvider {
    val homeMenuList = listOf<HomeMenuItem>(
        HomeMenuItem("축제", Config.CONTENT_TYPE_ID.FESTIVAL),
        HomeMenuItem("숙소", Config.CONTENT_TYPE_ID.STAY),
        HomeMenuItem("여행코스", Config.CONTENT_TYPE_ID.TOUR_COURSE),
        HomeMenuItem("음식점", Config.CONTENT_TYPE_ID.FOOD),
        HomeMenuItem("관광지", Config.CONTENT_TYPE_ID.TOUR_SPOT),
        HomeMenuItem("문화 시설", Config.CONTENT_TYPE_ID.CULTURE),
        HomeMenuItem("레포츠", Config.CONTENT_TYPE_ID.LEISURE_SPORTS),
        HomeMenuItem("쇼핑", Config.CONTENT_TYPE_ID.SHOPPING),
    )
}