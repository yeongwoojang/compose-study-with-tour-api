package com.example.tourmanage.ui.stay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isSuccess
import timber.log.Timber

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StayMainWidget(viewModel: StayViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var currentParentArea by rememberSaveable { mutableStateOf(ServerGlobal.getParentAreaList().first()) }
    var currentChildArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }

    var stayInfoList = viewModel.stayInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = currentParentArea) {
        Timber.i("Change Area | currentParentArea: $currentParentArea")
        viewModel.requestAreaList(currentParentArea.code)
        currentChildArea = null
    }

    Scaffold(
        topBar = {
            Header("숙소 찾기")
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(start = 16.dp, end = 16.dp)
        ) {
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
            if (stayInfoList.isSuccess()) {
                Timber.i("Show StayList")
                Spacer(modifier = Modifier.height(20.dp))
                StayListWidget(stayList = stayInfoList.value.data!!)
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
                            StayAreaIconWidget(currentParentArea, false)
                            Spacer(modifier = Modifier.width(10.dp))
                            if (currentChildArea != null) {
                                StayAreaIconWidget(currentChildArea, true)
                            }
                        }
                    }
                }
                ) {
                StayAreaDrawerContent(currentParentArea = currentParentArea, currentChildArea = currentChildArea) { areaItem, requestKey, isChild ->
                    if (isChild) {
                        currentChildArea = areaItem
                        viewModel.requestStayList(currentParentArea.code, currentChildArea?.code, )
                    } else {
                        currentParentArea = areaItem
                    }
                }
            }
        }
    }
}