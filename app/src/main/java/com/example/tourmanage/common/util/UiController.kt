package com.example.tourmanage.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.example.tourmanage.common.data.FragmentInteraction
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.extension.putExtra
import com.example.tourmanage.common.value.Config
import kotlin.reflect.KClass

object UiController {
    fun addActivity(context: Context?, targetActivity: KClass<out Activity>, data: IntentData? = null) {
        if (context == null) return
        Intent(context, targetActivity.java).apply {
            data?.let {
                putExtra(it)
            }
        }.let {
            val options = ActivityOptionsCompat.makeCustomAnimation(
                context,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            context.startActivity(it, options.toBundle())
        }
    }

    fun fragmentInteraction(interactionItem: FragmentInteraction) {
        val fragmentManager = interactionItem.fragmentManager
        val fragment = interactionItem.fragment
        val container = interactionItem.container

        fragmentManager.beginTransaction().apply {
            when (interactionItem.type) {
                Config.FRAGMENT_CHANGE_TYPE.ADD -> this.add(container, fragment)
                Config.FRAGMENT_CHANGE_TYPE.REPLACE -> this.replace(container, fragment)
                Config.FRAGMENT_CHANGE_TYPE.REMOVE -> this.remove(fragment)
            }
            this.commit()
        }
    }

}