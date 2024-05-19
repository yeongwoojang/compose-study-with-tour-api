package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.course.CourseMainWidget
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val curParentArea = intent.intentSerializable(Config.PASS_DATA.PARENT_AREA.value, AreaItem::class.java)
        val curChildArea = intent.intentSerializable(Config.PASS_DATA.CHILD_AREA.value, AreaItem::class.java)

        enableEdgeToEdge()
        setContent {
            TourManageTheme {
                CourseMainWidget(curParentArea = curParentArea, curChildArea = curChildArea)
            }
        }
    }
}