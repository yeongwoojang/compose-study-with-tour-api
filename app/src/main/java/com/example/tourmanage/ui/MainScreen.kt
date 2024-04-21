package com.example.tourmanage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.R
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.navigator.BottomNav
import com.example.tourmanage.ui.navigator.NavigationGraph
import com.example.tourmanage.viewmodel.MainViewModel
import timber.log.Timber

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    Timber.i("MainScreen()")
    LaunchedEffect(Unit) {
        viewModel.requestAreaList()
    }

    val areaCodes = viewModel.areaInfo.collectAsStateWithLifecycle()

    if (areaCodes.isSuccess()) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNav(
                    Modifier.height(60.dp),
                    containerColor = colorResource(id = R.color.white_smoke),
                    contentColor = Color.White,
                    indicatorColor = Color.Green,
                    navController = navController
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavigationGraph(navController)
            }
        }
    }

    if (areaCodes.isLoading()) {
        LoadingWidget()
    }
}