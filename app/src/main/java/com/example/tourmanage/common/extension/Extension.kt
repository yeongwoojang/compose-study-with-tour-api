package com.example.tourmanage.common.extension

import android.content.Intent
import com.example.tourmanage.common.data.IntentData

fun Intent.putExtra(data: IntentData) {
    data.map.keys.forEach {
        putExtra(it, data.map[it])
    }
}