package com.example.tourmanage.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.common.AreaIconWidget
import com.example.tourmanage.ui.main.MainRoute
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.MainHomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainHomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit,
    onClick: (HomeRoute, Any) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var curMainArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }
    var curSubArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo()

        launch {
            viewModel.curMainArea.collect {
                curMainArea = it
            }
        }

        launch {
            viewModel.curSubArea.collect {
                curSubArea = it
            }
        }
        launch {
            viewModel.getCachedArea()
        }
    }

    val subAreaListState = viewModel.subAreaList.collectAsStateWithLifecycle()
    val festivalListState = viewModel.festivalList.collectAsStateWithLifecycle()

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
    }

    if (festivalListState.isSuccess()) {
        val festivalList = festivalListState.value.data!!
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "진행중인 축제",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium,
                    ),
                )

                RollingBanner(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clickable { onClick(HomeRoute.FESTIVAL, festivalList) }
                    ,
                    itemList = festivalList
                )
            }
        }
    }

    if (bottomSheenOpenYn) {
        ModalBottomSheet(
            modifier = Modifier.height(600.dp),
            sheetState = sheetState,
            scrimColor = Color.Black.copy(alpha = .7f),
            windowInsets = WindowInsets(0, 0, 0, 0),
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion { onDismissMenu() }
            },
            dragHandle = {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "지역선택",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (curMainArea != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                curMainArea, false
                            )
                        }
                        if (curSubArea != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                curSubArea, true
                            )
                        }
                    }
                }
            }
        ) {
            AreaDrawerContent(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
                curMainArea = curMainArea,
                curChildArea = curSubArea,
                mainAreaList = ServerGlobal.getMainAreaList(),
                curChildAreaList = subAreaList,
                onClick = { areaItem, isChild ->
                    if (subAreaListState.isLoading()) {
                        return@AreaDrawerContent
                    }
                    viewModel.cacheArea(areaItem, isChild)
                }
            )
        }
    }
}