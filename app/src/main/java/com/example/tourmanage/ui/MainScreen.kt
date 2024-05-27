package com.example.tourmanage.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.navigator.BottomNav
import com.example.tourmanage.ui.navigator.NavigationGraph
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.common.AreaIconWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var isInit by remember {
        mutableStateOf(false)
    }
    val curParent = viewModel.curParentArea.collectAsStateWithLifecycle()
    val curChild = viewModel.curChildArea.collectAsStateWithLifecycle()

    var curParentItem by rememberSaveable {
      mutableStateOf<AreaItem?>(null)
    }
    var curChildItem by rememberSaveable {
        mutableStateOf<AreaItem?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.requestParentAreaList()
        viewModel.getCachedArea()
    }

    if (curParent.isSuccess()) {
        LaunchedEffect(key1 = curParent.value.data!!.code) {
            val parentArea = curParent.value.data!!
            Timber.i("Change Area | parentName: ${parentArea.name} | parentCode: ${parentArea.code}")
            if (isInit) {
                viewModel.removeCacheArea(true)
                curChildItem = null
            }
            if (!isInit) {
                isInit = true
            }

            viewModel.requestChildAreaList(parentArea.code)
            curParentItem = curParent.value.data
        }
    }

    if (curChild.isSuccess()) {
        LaunchedEffect(key1 = curChild.value.data?.name) {
            val childArea = curChild.value.data
            Timber.i("Change Child | childArea: $childArea")
            curChildItem = curChild.value.data
        }
    }

    var headerTitle by remember { mutableStateOf("HOME") }

    val areaCodes = viewModel.areaInfo.collectAsStateWithLifecycle()

    if (areaCodes.isSuccess()) {
        val navController = rememberNavController()
        navController.currentDestination
        Scaffold(
            topBar =
            {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    backgroundColor = colorResource(id = R.color.white_smoke),
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            text = headerTitle,
                            textAlign = TextAlign.Center
                        )

                    }
                )
            },
            bottomBar = {
                BottomNav(
                    Modifier.height(110.dp),
                    containerColor = colorResource(id = R.color.white_smoke),
                    contentColor = Color.White,
                    indicatorColor = Color.Green,
                    navController = navController
                ) {
                    headerTitle = it
                }
            }
        ) {
            Box(modifier = Modifier.padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())) {
                NavigationGraph(navController, curParentItem, curChildItem) {
                    isSheetOpen = true
                }
            }

            if (isSheetOpen) {
                ModalBottomSheet(
                    modifier = Modifier.height(600.dp),
                    sheetState = sheetState,
                    scrimColor = Color.Black.copy(alpha = .7f),
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion { isSheetOpen = false }
                    },
                    dragHandle = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "지역선택",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row() {
                                if (curParentItem != null) {
                                    AreaIconWidget(
                                        modifier = Modifier
                                            .width(60.dp)
                                            .wrapContentHeight(),
                                        curParentItem, false)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                if (curChildItem != null) {
                                    AreaIconWidget(modifier = Modifier
                                        .width(60.dp)
                                        .wrapContentHeight(),
                                        curChildItem, true)
                                }
                            }
                        }
                    }
                ) {
                    AreaDrawerContent(currentParentArea = curParentItem, currentChildArea = curChildItem) { areaItem, requestKey, isChild ->
                        viewModel.cacheArea(areaItem, isChild)
                    }
                }
            }

        }
    }

    if (areaCodes.isLoading()) {
        LoadingWidget()
    }
}