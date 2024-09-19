package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FestivalMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData = intent.intentSerializable(Config.PASS_DATA.DATA.value, java.util.ArrayList::class.java)

        var mainFestival: ArrayList<FestivalItem>? = null
        if (receivedData is ArrayList<*>) {
            mainFestival = receivedData as? ArrayList<FestivalItem>
        }

        setContent {
            TourManageTheme {
                if (mainFestival != null) {
//                    FestivalNavHost(
//                        mainFestival = mainFestival
//                    )
                }
            }
        }
    }
}


