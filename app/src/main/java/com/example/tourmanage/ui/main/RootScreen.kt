package com.example.tourmanage.ui.main

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.home.OverlayRoute
import com.example.tourmanage.viewmodel.RootViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {

    val areaCodes = viewModel.areaCodesState.collectAsStateWithLifecycle()
    var showOverlay by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val overlayNavController = rememberNavController()

    var isInit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            viewModel.exceptionState.collect { throwable ->
                Toast.makeText(context, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        PermissionUtils.getLocation(context)
            .collect {
                isInit = true
            }
    }

    Timber.i("areaCodes: $areaCodes")
    if (areaCodes.isLoading() || !isInit) {
        LoadingWidget()
    }

    if (areaCodes.isSuccess() && isInit) {
        MainNavHost(
            showOverlay = {
                showOverlay = true
                val mainFestival = Uri.encode(Gson().toJson(it))
                Timber.i("TEST_LOG | data: $mainFestival")
                Timber.i("TEST_LOG | overlayNavController: $overlayNavController")
                overlayNavController.navigate("${OverlayRoute.FESTIVAL.route}/$mainFestival")
//                overlayNavController.navigateToFestival(mainFestival)
            }
        )
    }

    if (showOverlay) {
        OverlayNavHost(
            navController = overlayNavController
        )
    }
}