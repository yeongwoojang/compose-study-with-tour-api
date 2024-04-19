package com.example.tourmanage.common.value

object Config {

    const val HOME = "HOME"
    const val WISH = "WISH"
    const val AREA = "AREA"

    const val MAIN_MENU_KEY = "MENU"
    const val STAY_INFO = "STAY_INFO"

    enum class FRAGMENT_CHANGE_TYPE(val value: String) {
        ADD("ADD"),
        REPLACE("REPLACE"),
        REMOVE("REMOVE")
    }

    enum class CARD_TYPE(value: String) {
        MENU_STAY("STAY"),
        TYPE_B("TYPE_B"),
    }

    enum class ARRANGE_TYPE(value: String) {
        A("A"), //_ 제목순
        C("C"), //_ 수정일순
        D("D"), //_ 생성일순
        //_ 아래 부터는 대표 이미지가 반드시 있는 정렬
        O("O"), //_ 제목순
        Q("Q"), //_ 제목순
        R("R")//_ 생성일순
    }
}