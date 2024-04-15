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
}