package com.example.tourmanage.presenter.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.presenter.components.LoadingWidget
import com.example.tourmanage.presenter.viewmodel.RootViewModel
import kotlinx.coroutines.launch

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