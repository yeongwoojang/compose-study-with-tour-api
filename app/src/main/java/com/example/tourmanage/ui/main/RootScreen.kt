package com.example.tourmanage.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isError
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isReady
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.RootViewModel
import timber.log.Timber

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {

    val areaCodes = viewModel.areaCodesState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAreaList()
    }

    Timber.i("areaCodes: $areaCodes")
    if (areaCodes.isLoading() || areaCodes.isReady()) {
        LoadingWidget()
    }

    if (areaCodes.isSuccess()) {
        MainNavHost()
    }

    if (areaCodes.isError()) {

    }
}