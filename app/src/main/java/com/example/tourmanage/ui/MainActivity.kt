package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.main.MainNavHost
import com.example.tourmanage.ui.main.RootScreen
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
        PermissionUtils.getLocation(this)
    }

    override fun onStart() {
        super.onStart()
        PermissionUtils.requestLocation(this)
    }
}