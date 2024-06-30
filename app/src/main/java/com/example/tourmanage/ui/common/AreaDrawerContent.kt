package com.example.tourmanage.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.*

@Composable
fun AreaDrawerContent(
    modifier: Modifier = Modifier,
    curMainArea: AreaItem?,
    curChildArea: AreaItem?,
    mainAreaList: List<AreaItem>,
    curChildAreaList: List<AreaItem>?,
    onClick: (areaItem: AreaItem, isChild: Boolean) -> Unit
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        var scrollPosition = mainAreaList.indexOf(curMainArea)
        if (scrollPosition == -1) scrollPosition = 0
        listState.scrollToItem(scrollPosition)
    }

    Box(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding()
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
                    count = mainAreaList.size,
                    key = {index ->
                        mainAreaList[index]
                    }
                ) { index ->
                    val mainArea = mainAreaList[index]
                    AreaItemLayout(
                        areaItem = mainArea,
                        currentParentArea = curMainArea,
                        onClick = {
                            onClick(mainArea, false)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            if (curChildAreaList != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = curChildAreaList,
                        itemContent = { childArea ->
                            AreaItemLayout(
                                areaItem = childArea,
                                isChild = true,
                                currentParentArea = curMainArea,
                                currentChildArea = curChildArea,
                                onClick = {
                                    onClick(childArea, true)
                                }
                            )
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun AreaItemLayout(
    modifier: Modifier = Modifier,
    areaItem: AreaItem?,
    isChild: Boolean = false,
    currentParentArea: AreaItem?,
    currentChildArea: AreaItem? = null,
    onClick: () -> Unit
) {
    val backgroundColor = if (isChild) {
        if (currentChildArea?.name == areaItem?.name && currentChildArea?.code == areaItem?.code) {
            colorResource(id = R.color.cornflower_blue)
        } else {
            colorResource(id = R.color.gainsboro)
        }
    } else {
        if (currentParentArea?.name == areaItem?.name && currentParentArea?.code == areaItem?.code) {
            colorResource(id = R.color.light_coral)
        } else {
            colorResource(id = R.color.gainsboro)
        }
    }

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .width(80.dp)
            .wrapContentHeight()
            .padding(10.dp)
            .noRippleClickable {
                if (!isChild) {
                    if (currentParentArea?.code != areaItem?.code) {
                        areaItem?.code?.let {
                            onClick()
                        }
                    }
                } else {
                    if (currentChildArea?.code != areaItem?.code) {
                        areaItem?.code?.let {
                            onClick()
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = areaItem?.name.isEmptyString(),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
    }
}