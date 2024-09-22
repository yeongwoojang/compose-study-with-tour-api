package com.example.tourmanage.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit,
    onClick: (OverlayRoute, Any?) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var subAreaList by remember { mutableStateOf<List<AreaItem>?>(null) }
    var areaCodeMap by remember { mutableStateOf<Pair<AreaItem?, AreaItem?>>(Pair(null, null)) }
    var currentMenu by remember { mutableStateOf(Config.CONTENT_TYPE_ID.FESTIVAL) }

    val currentArea = viewModel.currentArea.collectAsStateWithLifecycle()
    val subAreaListState = viewModel.subAreaList.collectAsStateWithLifecycle()
    val posterList = viewModel.posterListFlow.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    if (subAreaListState.isSuccess()) {
        subAreaList = subAreaListState.value.data!!
    }

    if (currentArea.isSuccess()) {
        areaCodeMap = currentArea.value.data!!
    }

    LaunchedEffect(currentMenu) {
        viewModel.changeMenu(currentMenu)
    }
    LaunchedEffect(areaCodeMap) {
        listState.scrollToItem(0)
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
                            modifier = Modifier.fillMaxWidth().height(250.dp)
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { },
                                contentScale = ContentScale.FillBounds,
                                model = item.imgUrl,
                                contentDescription = ""
                            )
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

            posterList.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item{
                            PageLoader()
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item{
                            LoadingNextPageItem()
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