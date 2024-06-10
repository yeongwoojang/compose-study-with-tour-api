package com.example.tourmanage.ui.stay

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.StayViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StayMainWidget(viewModel: StayViewModel = hiltViewModel(), curParentArea: AreaItem?, curChildArea: AreaItem?) {
    var stayInfoList = viewModel.stayInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.requestStayList(curParentArea?.code, curChildArea?.code)
    }
    Scaffold(
        topBar = {
            val context = LocalContext.current
            Header("숙소 찾기", Config.HEADER_BUTTON_TYPE.HOME) {
                (context as Activity).finish()
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = it.calculateTopPadding() + 20.dp, end = 16.dp, bottom = it.calculateBottomPadding())
        ) {
            if (stayInfoList.isSuccess()) {
                StayListWidget(stayList = stayInfoList.value.data!!)
            }
        }
    }
}