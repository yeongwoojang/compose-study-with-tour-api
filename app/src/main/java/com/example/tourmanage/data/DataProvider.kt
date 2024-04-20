package com.example.tourmanage.data

import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config

object DataProvider {
    val puppyList = listOf(
        Puppy("코코", R.drawable.puppy1, "코코코코코 코 코코코 입!"),
        Puppy("보리", R.drawable.puppy2, "보리 보리 쌀"),
        Puppy("초코", R.drawable.puppy3, "초코송이 먹고싶당"),
        Puppy("콩이", R.drawable.puppy4, "나는 콩을 안좋아하지만 콩이는 좋아!"),
        Puppy("사랑이", R.drawable.puppy5, "사랑이 덕분에 집에 사랑이 가득~"),
        Puppy("달이", R.drawable.puppy6, "달 달 무슨 달 쟁반같이 둥근 달"),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
        Puppy("별이", R.drawable.puppy7, "별.. 별.. 별.."),
    )

    val cardList = listOf(
        CardItem(R.drawable.puppy1),
        CardItem(R.drawable.puppy2),
        CardItem(R.drawable.puppy3),
        CardItem(R.drawable.puppy4),
        CardItem(R.drawable.puppy5),
        CardItem(R.drawable.puppy6),
        CardItem(R.drawable.puppy7),
    )

    val homeMenuList = listOf<HomeMenuItem>(
        HomeMenuItem("축제", Config.HOME_MENU_TYPE.FESTIVAL, R.drawable.festival_menu),
        HomeMenuItem("숙소", Config.HOME_MENU_TYPE.STAY, R.drawable.stay_menu),
        HomeMenuItem("걷기", Config.HOME_MENU_TYPE.WALK, R.drawable.walk_menu),
        HomeMenuItem("자전거", Config.HOME_MENU_TYPE.RIDING, R.drawable.riding_menu),
        HomeMenuItem("관광지", Config.HOME_MENU_TYPE.TOUR_SPOT, R.drawable.tour_spot_menu),
        HomeMenuItem("문화 시설", Config.HOME_MENU_TYPE.CULTURE, R.drawable.culture_menu),
    )
}