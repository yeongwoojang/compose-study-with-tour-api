package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.main.RootScreen
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TourManageTheme {
                RootScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStart() {
        super.onStart()
        PermissionUtils.requestLocation(this)
    }
}