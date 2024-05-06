package com.example.tourmanage.ui.staydetail

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.extension.*
import com.example.tourmanage.ui.*
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.StayDetailViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailLayout(stayItem: StayItem?, viewModel: StayDetailViewModel = hiltViewModel()) {
    Timber.i("DetailLayout() | stayItem: $stayItem")

    if (stayItem == null) {
        return
    }

    val context = LocalContext.current
    val activity = context as StayDetailActivity
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.requestStayDetailInfo(stayItem.contentId, stayItem.contentTypeId)
        viewModel.requestOptionInfo(stayItem.contentId, stayItem.contentTypeId)
    }

    val optionItem = viewModel.optionInfo.collectAsStateWithLifecycle()
    val stayDetailItem = viewModel.stayDetailInfo.collectAsStateWithLifecycle()

    val scrollState = rememberLazyListState()
    var totalRoomCount by remember { mutableStateOf(0) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = scrollState, key2 = totalRoomCount) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect { index ->
                Timber.i("totalRoomCount: $totalRoomCount")
                if (totalRoomCount == 0) {
                    return@collect
                }
                if (totalRoomCount > 3) {
                    if (index == 2) {
                        isBottomSheetOpen = true
                    } else if (index == 0) {
                        isBottomSheetOpen = false
                    }
                } else {
                    isBottomSheetOpen = true
                }
            }
    }

    if (stayDetailItem.isSuccess()) {
        val detailData = stayDetailItem.value.data!!
        LazyColumn(
            state = scrollState
        ) {
            item {
                StayTopImage(detailData, activity)
            }
            item {
                StayIntro(detailData)
                StayOverview(detailData, Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp))
            }
            if (optionItem.isSuccess()) {
                val optionInfos = optionItem.value.data!!
                totalRoomCount = optionInfos.size
                item {
                    Column(modifier = Modifier
                        .background(colorResource(id = R.color.white_smoke))
                        .padding(start = 10.dp, end = 10.dp, top = 8.dp))
                    {
                        optionInfos.forEachIndexed { index, optionItem ->
                            StayRooms(optionItem, index, totalRoomCount, Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp))
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier
                    .background(colorResource(id = R.color.white_smoke))
                    .height(60.dp))
            }
        }
    }

    if (optionItem.isSuccess()) {
        AnimatedVisibility(
            visible = isBottomSheetOpen,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Bottom),
            enter = slideInVertically(initialOffsetY = {
                it
            }),
            exit = slideOutVertically(targetOffsetY = {
                it
            })
        ) {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .background(colorResource(id = R.color.white_smoke))
                    .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            )
            {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.lightpink)
                    ),
                    onClick = {
                        coroutineScope.launch {
                            scrollState.scrollToItem(2)
                        }
                    }
                ) {
                    Text(text = "객실 선택", color = Color.White)
                }
            }
        }
    }

    if (optionItem.isLoading()) {
        LoadingWidget()
    }
}