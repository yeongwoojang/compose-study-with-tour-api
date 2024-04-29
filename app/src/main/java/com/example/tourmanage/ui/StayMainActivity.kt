package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.stay.StayMainWidget
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StayMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        val menu = extras?.getString(Config.MAIN_MENU_KEY) ?: "MY APP"

        val viewModel by viewModels<MainViewModel>()
        setContent {
            TourManageTheme {
                StayMainWidget()
            }
        }
    }
}