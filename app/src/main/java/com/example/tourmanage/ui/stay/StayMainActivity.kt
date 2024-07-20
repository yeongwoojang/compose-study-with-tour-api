package com.example.tourmanage.ui.stay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.StayMenuItem
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import timber.log.Timber

class StayMainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData: StayItem? = intent.intentSerializable(Config.PASS_DATA.DATA.value, StayItem::class.java)

        setContent {
            TourManageTheme {
                val navController = rememberNavController()
                StayNavHost(
                    navController = navController,
                    startDestination = if (receivedData != null) StayMenuItem.Detail.route else StayMenuItem.Main.route,
                    data = receivedData
                )
            }
        }
    }
}