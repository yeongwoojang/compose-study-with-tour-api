package com.example.tourmanage.common.value

import androidx.datastore.preferences.core.stringPreferencesKey

object Config {
    enum class PASS_DATA(val value: String) {
        PARENT_AREA("PARENT_AREA"),
        CHILD_AREA("CHILD_AREA"),
        DATA("DATA"),
    }

    enum class FRAGMENT_CHANGE_TYPE(val value: String) {
        ADD("ADD"),
        REPLACE("REPLACE"),
        REMOVE("REMOVE")
    }

    enum class CARD_TYPE(val value: String) {
        MENU_STAY("STAY"),
        MENU_TOUR("TOUR"),
    }

    enum class ARRANGE_TYPE(val value: String) {
        A("A"), //_ 제목순
        C("C"), //_ 수정일순
        D("D"), //_ 생성일순
        //_ 아래 부터는 대표 이미지가 반드시 있는 정렬
        O("O"), //_ 제목순
        Q("Q"), //_ 제목순
        R("R")//_ 생성일순
    }

    enum class CONTENT_TYPE_ID(val value: String) {
        TOUR_SPOT("12"),
        CULTURE("14"),
        FESTIVAL("15"),
        TOUR_COURSE("25"),
        LEISURE_SPORTS("28"),
        STAY("32"),
        SHOPPING("38"),
        FOOD("39"),
    }

    enum class HOME_MENU_TYPE(val value: String) {
        FESTIVAL("FESTIVAL"),
        STAY("FESTIVAL"),
        COURSE("COURSE"),
        FOOD("FOOD"),
        TOUR_SPOT("TOUR_SPOT"),
        CULTURE("CULTURE"),
        LEISURE("LEISURE"),
        SHOPPING("SHOPPING"),
    }

    enum class HEADER_BUTTON_TYPE(val value: String) {
        HOME("HOME"),
        CLOSE("CLOSE")
    }

    val PARENT_AREA = stringPreferencesKey(
        name = "PARENT_AREA"
    )

    val CHILD_AREA = stringPreferencesKey(
        name = "CHILD_AREA"
    )
}