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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.common.AreaIconWidget
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
    onClick: (HomeRoute, Any?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }

    var areaCodeMap by remember { mutableStateOf<Pair<AreaItem?, AreaItem?>>(Pair(null, null)) }

    val currentArea = viewModel.currentArea.collectAsStateWithLifecycle()
    val subAreaListState = viewModel.subAreaList.collectAsStateWithLifecycle()
    val mainFestivalState = viewModel.festivalList.collectAsStateWithLifecycle()
    val stayListState = viewModel.stayList.collectAsStateWithLifecycle()

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
    }

    if (currentArea.isSuccess()) {
        areaCodeMap = currentArea.value.data!!
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (mainFestivalState.isSuccess()) {
            val festivalList = mainFestivalState.value.data!!
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
                        .clickable { onClick(HomeRoute.FESTIVAL, festivalList) }
                    ,
                    itemList = festivalList
                )
            }
        }

        if (stayListState.isSuccess()) {
            val stayList = stayListState.value.data!!
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
                                onClick(HomeRoute.STAY, null)
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
                                    onClick(HomeRoute.STAY, item)
                                },
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            GlideImage(
                                model = item.fullImageUrl,
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

                        if (areaCodeMap.first != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                areaCodeMap.first, false
                            )
                        }
                        if (areaCodeMap.second != null) {
                            AreaIconWidget(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                areaCodeMap.second, true
                            )
                        }
                    }
                }
            }
        ) {
            AreaDrawerContent(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
                curMainArea = areaCodeMap.first,
                curChildArea = areaCodeMap.second,
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