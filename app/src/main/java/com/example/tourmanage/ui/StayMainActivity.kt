package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.stay.StayMainWidget
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StayMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val curParentArea = intent.intentSerializable(Config.PASS_DATA.PARENT_AREA.value, com.example.tourmanage.common.data.server.item.AreaItem::class.java)
        val curChildArea = intent.intentSerializable(Config.PASS_DATA.CHILD_AREA.value, com.example.tourmanage.common.data.server.item.AreaItem::class.java)

        setContent {
            TourManageTheme {
                StayMainWidget(curParentArea = curParentArea, curChildArea = curChildArea)
            }
        }
    }
}