package com.example.tourmanage.ui.stay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourmanage.ui.common.DrawerOpenTextField
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.StayViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material.Divider
import androidx.compose.ui.Alignment
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.extension.isEmptyString
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StayMainWidget(viewModel: StayViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var currentAreaCode by rememberSaveable { mutableStateOf(ServerGlobal.getAreaCodeList().first().code.isEmptyString()) }

    LaunchedEffect(key1 = currentAreaCode) {
        Timber.i("Change Area | currentAreaCode: $currentAreaCode")
        viewModel.requestAreaList(currentAreaCode)
    }

    Scaffold(
        topBar = {
            Header("숙소 찾기")
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
                Text(text = "여행지를 찾아보세요.",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                DrawerOpenTextField("지역명 검색", onClick = {
                    isSheetOpen = true
                })
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
                    Column(modifier = Modifier
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
                    }
                }
                ) {
                StayAreaDrawerContent(currentAreaCode = currentAreaCode) { areaItem, requestKey ->
                    currentAreaCode = areaItem.code!!
                }
            }
        }
    }
}