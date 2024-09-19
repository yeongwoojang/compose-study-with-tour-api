package com.example.tourmanage.ui.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.RootViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {

    val areaCodes = viewModel.areaCodesState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val locationFlow by viewModel.locationFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        launch {
            viewModel.exceptionState.collect { throwable ->
                Toast.makeText(context, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (areaCodes.isLoading() || !locationFlow) {
        LoadingWidget()
    } else {
        MainNavHost()
    }
}