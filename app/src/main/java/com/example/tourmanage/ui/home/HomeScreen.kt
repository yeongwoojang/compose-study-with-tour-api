package com.example.tourmanage.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit,
    onClick: (OverlayRoute, Any?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }
    var areaCodeMap by remember { mutableStateOf<Pair<AreaItem?, AreaItem?>>(Pair(null, null)) }

    val currentArea = viewModel.currentArea.collectAsStateWithLifecycle()
    val subAreaListState = viewModel.subAreaList.collectAsStateWithLifecycle()

    val posterItemFlow = viewModel.posterFlow.collectAsStateWithLifecycle()

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
    }

    if (currentArea.isSuccess()) {
        areaCodeMap = currentArea.value.data!!
    }

    if (posterItemFlow.isSuccess()) {
        val posterItem = posterItemFlow.value.data!!
        val festivalList = posterItem.filter { it.contentTypeId == Config.CONTENT_TYPE_ID.FESTIVAL.value }
        val stayList = posterItem.filter { it.contentTypeId == Config.CONTENT_TYPE_ID.STAY.value }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 16.dp),
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
                        .padding(horizontal = 16.dp)
                        .clickable { onClick(OverlayRoute.FESTIVAL, festivalList) }
                    ,
                    itemList = festivalList
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "숙소 정보",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Normal,
                        ))
                    Box(
                        modifier = Modifier
                            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(5.dp)
                            .clickable {
                                onClick(OverlayRoute.STAY, null)
                            }
                    ) {
                        Text(
                            text = "더보기",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = spoqaHanSansNeoFont,
                                fontWeight = FontWeight.Normal,
                            ))
                    }

                }
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        count = stayList.size,
                        key = { index ->
                            stayList[index].contentId.orEmpty()
                        }
                    ) { index ->
                        val item = stayList[index]
                        val isLast = index == stayList.size - 1
                        val isFirst = index == 0
                        val startPadding = if (isFirst) 16.dp else 0.dp
                        val endPadding = if (isLast) 16.dp else 0.dp
                        Column(
                            modifier = Modifier
                                .padding(start = startPadding, end = endPadding)
                                .clickable {
                                    onClick(OverlayRoute.STAY, item)
                                },
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            GlideImage(
                                model = item.imgUrl,
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                            Text(
                                modifier = Modifier.width(150.dp),
                                text = item.title.toString(),
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Normal,
                                )
                            )
                        }
                    }
                }
            }
        }
    } else {
        LoadingWidget()
    }

    if (bottomSheenOpenYn) {
        ModalBottomSheet(
//            modifier = Modifier.height(600.dp),
            sheetState = sheetState,
            windowInsets = WindowInsets(0, 0, 0, 0),
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion { onDismissMenu() }
            }, dragHandle = { BottomSheetDefaults.DragHandle()},
        ) {
            AreaDrawerContent(
                modifier = Modifier.height(600.dp),
                currentArea = areaCodeMap.first,
                currentSigungu = areaCodeMap.second,
                areaList = ServerGlobal.getMainAreaList(),
                sigunguList = subAreaList,
                onClick = { areaItem, isSigungu ->
                    if (subAreaListState.isLoading()) {
                        return@AreaDrawerContent
                    }
                    viewModel.onChangeArea(areaItem, isSigungu)
                }
            )
        }
    }
}