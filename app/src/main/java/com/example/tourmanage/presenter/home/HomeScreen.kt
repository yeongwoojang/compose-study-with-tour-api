package com.example.tourmanage.presenter.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.presenter.common.AreaDrawerContent
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.presenter.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit,
    onClick: (OverlayRoute, PosterItem) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }
    var areaCodeMap by remember { mutableStateOf<Pair<AreaItem?, AreaItem?>>(Pair(null, null)) }
    var currentMenu by rememberSaveable { mutableStateOf(Config.CONTENT_TYPE_ID.FESTIVAL) }

    val currentArea = viewModel.currentArea.collectAsStateWithLifecycle()
    val subAreaListState = viewModel.subAreaList.collectAsStateWithLifecycle()
    val posterList = viewModel.posterListFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    var loadingState by remember {
        mutableStateOf(false)
    }

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
    }

    if (currentArea.isSuccess()) {
        areaCodeMap = currentArea.value.data!!
    }

    LaunchedEffect(currentMenu) {
        viewModel.changeMenu(currentMenu)
    }

    LaunchedEffect(loadingState) {
        if (loadingState) {
            listState.scrollToItem(0)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        MenuItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            currentMenu = currentMenu,
            onClickMenu = {
                currentMenu = it
            }
        )

        if (posterList.loadState.refresh is LoadState.Loading) {
            loadingState = true
            PageLoader(modifier = Modifier.fillMaxSize())
        } else {
            loadingState = false
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(posterList.itemCount) { index ->
                    val item = posterList[index] // index를 통해 아이템을 가져옴
                    item?.let {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            ) {
                                if (item.imgUrl.isNotEmpty()) {
                                    GlideImage(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                val overlayRoute = when (currentMenu) {
                                                    Config.CONTENT_TYPE_ID.FESTIVAL -> OverlayRoute.FESTIVAL
                                                    Config.CONTENT_TYPE_ID.STAY -> OverlayRoute.STAY
                                                    Config.CONTENT_TYPE_ID.TOUR_COURSE -> OverlayRoute.TOUR_COURSE
                                                    Config.CONTENT_TYPE_ID.FOOD -> OverlayRoute.FOOD
                                                    Config.CONTENT_TYPE_ID.TOUR_SPOT -> OverlayRoute.TOUR_SPOT
                                                    Config.CONTENT_TYPE_ID.CULTURE -> OverlayRoute.CULTURE
                                                    Config.CONTENT_TYPE_ID.LEISURE_SPORTS -> OverlayRoute.LEISURE_SPORTS
                                                    Config.CONTENT_TYPE_ID.SHOPPING -> OverlayRoute.SHOPPING
                                                }
                                                onClick(overlayRoute, item)
                                            },
                                        contentScale = ContentScale.FillBounds,
                                        model = item.imgUrl,
                                        contentDescription = ""
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                            .clickable {
                                                val overlayRoute = when (currentMenu) {
                                                    Config.CONTENT_TYPE_ID.FESTIVAL -> OverlayRoute.FESTIVAL
                                                    Config.CONTENT_TYPE_ID.STAY -> OverlayRoute.STAY
                                                    Config.CONTENT_TYPE_ID.TOUR_COURSE -> OverlayRoute.TOUR_COURSE
                                                    Config.CONTENT_TYPE_ID.FOOD -> OverlayRoute.FOOD
                                                    Config.CONTENT_TYPE_ID.TOUR_SPOT -> OverlayRoute.TOUR_SPOT
                                                    Config.CONTENT_TYPE_ID.CULTURE -> OverlayRoute.CULTURE
                                                    Config.CONTENT_TYPE_ID.LEISURE_SPORTS -> OverlayRoute.LEISURE_SPORTS
                                                    Config.CONTENT_TYPE_ID.SHOPPING -> OverlayRoute.SHOPPING
                                                }
                                                onClick(overlayRoute, item)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "이미지를 불러올 수 없습니다.",
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Medium,
                                                fontFamily = spoqaHanSansNeoFont,
                                            )
                                        )
                                    }
                                }
                            }

                            Text(
                                text = item.title,
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = spoqaHanSansNeoFont,
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
            sheetState = sheetState,
            windowInsets = WindowInsets(0, 0, 0, 0),
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion { onDismissMenu() }
            },
            dragHandle = { BottomSheetDefaults.DragHandle() },
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

@Composable
fun PageLoader(modifier: Modifier = Modifier) {
    Timber.i("YW | PageLoader")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "로딩중",
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CircularProgressIndicator(Modifier.padding(top = 10.dp))
    }
}

@Composable
fun LoadingNextPageItem(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}