package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()
        enableEdgeToEdge()
        setContent {
            TourManageTheme {
                MainScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        PermissionUtils.getLocation(this)
    }

    override fun onStart() {
        super.onStart()
        PermissionUtils.requestLocation(this)
    }
}