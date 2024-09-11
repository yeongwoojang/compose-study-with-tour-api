package com.example.tourmanage.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.*
import timber.log.Timber

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
                    key = { index ->
                        mainAreaList[index]
                    }
                ) { index ->
                    val mainArea = mainAreaList[index]
                    AreaItemLayout(
                        modifier = Modifier.width(80.dp),
                        areaItem = mainArea,
                        curMainArea = curMainArea,
                        onClick = {
                            onClick(mainArea, false)
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
                                modifier = Modifier.width(80.dp),
                                areaItem = childArea,
                                isSub = true,
                                curMainArea = curMainArea,
                                curSubArea = curChildArea,
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
    isSub: Boolean = false,
    curMainArea: AreaItem?,
    curSubArea: AreaItem? = null,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSub) {
        if (curSubArea?.name == areaItem?.name && curSubArea?.code == areaItem?.code) {
            colorResource(id = R.color.cornflower_blue)
        } else {
            colorResource(id = R.color.gainsboro)
        }
    } else {
        if (curMainArea?.name == areaItem?.name && curMainArea?.code == areaItem?.code) {
            colorResource(id = R.color.light_coral)
        } else {
            colorResource(id = R.color.gainsboro)
        }
    }

    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(color = backgroundColor, shape = RoundedCornerShape(6.dp))
            .padding(10.dp)
            .noRippleClickable {
                if (!isSub) {
                    if (curMainArea?.code != areaItem?.code) {
                        onClick()
                    }
                } else {
                    if (curSubArea?.code != areaItem?.code) {
                        onClick()
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