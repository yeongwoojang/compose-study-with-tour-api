package com.example.tourmanage.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.presenter.main.RootScreen
import com.example.tourmanage.presenter.ui.theme.TourManageTheme
import com.example.tourmanage.presenter.viewmodel.RootViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<RootViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TourManageTheme {
                Surface {
                    RootScreen()
                }
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