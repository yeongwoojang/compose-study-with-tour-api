package com.example.tourmanage.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.data.server.item.AreaItem

@Composable
fun AreaDrawerContent(
    modifier: Modifier = Modifier,
    currentArea: AreaItem?,
    currentSigungu: AreaItem?,
    areaList: List<AreaItem>,
    sigunguList: List<AreaItem>?,
    onClick: (areaItem: AreaItem, isSigungu: Boolean) -> Unit
) {

    val listState = rememberLazyListState()

    var selectedArea by remember {
        mutableStateOf(currentArea)
    }
    var selectedSigungu by remember {
        mutableStateOf(currentSigungu)
    }

    LaunchedEffect(Unit) {
        var scrollPosition = areaList.indexOf(currentArea)
        if (scrollPosition == -1) scrollPosition = 0
        listState.scrollToItem(scrollPosition)
    }

    Box(
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = areaList.size,
                    key = { index ->
                        areaList[index]
                    }
                ) { index ->
                    val areaItem = areaList[index]
                    val iconColor = if (areaItem.code == selectedArea?.code && areaItem.name == selectedArea?.name) 0xFFFFB6C1 else 0xFFDCDCDC
                    AreaIcon(
                        modifier = Modifier.width(80.dp),
                        areaItem = areaItem,
                        color = iconColor,
                        onClick = { selectedItem ->
                            selectedArea = selectedItem
                            //TODO 해당 부분 테스트 및 검토 필요
                            if (currentSigungu?.code != areaItem.code && currentSigungu?.name != areaItem.name) {
                                onClick(areaItem, false)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            VerticalDivider(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            if (sigunguList != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = sigunguList,
                        itemContent = { sigungu ->
                            val iconColor = if (sigungu.code == selectedSigungu?.code && sigungu.name == selectedSigungu?.name) 0xFFF08080 else 0xFFDCDCDC
                            AreaIcon(
                                modifier = Modifier.width(80.dp),
                                areaItem = sigungu,
                                color = iconColor,
                                onClick = { selectedItem ->
                                    selectedSigungu = selectedItem
                                    //TODO 해당 부분 테스트 및 검토 필요
                                    if (currentSigungu?.code != sigungu.code && currentSigungu?.name != sigungu.name) {
                                        onClick(sigungu, true)
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}