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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import com.example.tourmanage.common.ServerGlobal
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
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var currentParentArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }
    var currentChildArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }

    LaunchedEffect(Unit) {
        viewModel.requestParentAreaList()
    }

    LaunchedEffect(key1 = currentParentArea) {
        Timber.i("Change Area | currentParentArea: $currentParentArea")
        viewModel.requestChildAreaList(currentParentArea?.code)
        currentChildArea = null
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

                    },
//                    actions = {
//                        IconButton(
//                            modifier = Modifier.padding(top = 20.dp),
//                            onClick = {
//                                isSheetOpen = true
//                            }) {
//                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
//                        }
//                    }
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
                NavigationGraph(navController, currentParentArea, currentChildArea) {
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
                                if (currentParentArea != null) {
                                    AreaIconWidget(
                                        modifier = Modifier
                                            .width(60.dp)
                                            .wrapContentHeight(),
                                        currentParentArea, false)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                if (currentChildArea != null) {
                                    AreaIconWidget(modifier = Modifier
                                        .width(60.dp)
                                        .wrapContentHeight(),
                                        currentChildArea, true)
                                }
                            }
                        }
                    }
                ) {
                    AreaDrawerContent(currentParentArea = currentParentArea, currentChildArea = currentChildArea) { areaItem, requestKey, isChild ->
                        if (isChild) {
                            currentChildArea = areaItem
                            ServerGlobal.setCurrentChildArea(currentChildArea)
                        } else {
                            currentParentArea = areaItem
                            ServerGlobal.setCurrentParentArea(currentParentArea)
                        }
                    }
                }
            }

        }
    }

    if (areaCodes.isLoading()) {
        LoadingWidget()
    }
}