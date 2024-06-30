package com.example.tourmanage.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.common.AreaIconWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.MainHomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainHomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var curMainArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }
    var curSubArea by rememberSaveable { mutableStateOf<AreaItem?>(null) }

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }

    LaunchedEffect(Unit) {
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

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "지역선택",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Row {
                        if (curMainArea != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .width(60.dp)
                                    .wrapContentHeight(),
                                curMainArea, false
                            )
                        }
                        if (curSubArea != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .width(60.dp)
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

@Preview
@Composable
fun HomeScreenPreview() {

}