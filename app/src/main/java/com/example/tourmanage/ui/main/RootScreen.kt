package com.example.tourmanage.ui.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isError
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isReady
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.RootViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun RootScreen(viewModel: RootViewModel = hiltViewModel()) {

    val areaCodes = viewModel.areaCodesState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAreaList()

        launch {
            viewModel.exceptionState.collect { throwable ->
                Toast.makeText(context, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Timber.i("areaCodes: $areaCodes")
    if (areaCodes.isLoading() || areaCodes.isReady()) {
        LoadingWidget()
    }

    if (areaCodes.isSuccess()) {
        MainNavHost()
    }
}