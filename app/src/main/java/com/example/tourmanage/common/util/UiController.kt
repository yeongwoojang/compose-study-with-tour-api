package com.example.tourmanage.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.extension.putExtra
import kotlin.reflect.KClass

object UiController {
    fun addActivity(context: Context?, targetActivity: KClass<out Activity>, data: IntentData? = null) {
        if (context == null) return
        Intent(context, targetActivity.java).apply {
            data?.let {
                putExtra(it)
            }
        }.let {
            context.startActivity(it)
        }
    }

    fun changeActivity() {

    }

    fun addFragment() {

    }

    fun changeFragment() {

    }
}