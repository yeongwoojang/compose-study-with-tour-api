package com.example.tourmanage.presenter.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.presenter.components.LoadingWidget
import com.example.tourmanage.presenter.home.RollingBanner
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.presenter.viewmodel.FestivalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalDetailScreen(
    viewModel: FestivalViewModel = hiltViewModel(),
    posterItem: PosterItem?
) {

    val festivalDetailState = viewModel.festivalDetailInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.requestFestivalDetail(posterItem?.contentId)
    }

    if (festivalDetailState.isSuccess()) {
        val festivalDetail = festivalDetailState.value.data!!

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberStandardBottomSheetState())

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 500.dp,
            sheetShape = RoundedCornerShape(
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
                topStart = 16.dp,
                topEnd = 16.dp
            ),
            sheetDragHandle = {

            },
            sheetContent = {
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .heightIn(min = 550.dp, max = 600.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                if (festivalDetail.addr1.isNotEmpty()) {
                                    IconText(
                                        text = festivalDetail.addr1,
                                        icon = Icons.Filled.LocationOn
                                    )
                                }
                                if (festivalDetail.tel.isNotEmpty()) {
                                    IconText(
                                        text = festivalDetail.tel,
                                        icon = Icons.Filled.Call,
                                    )
                                }
                                if (festivalDetail.homePageUrl.isNotEmpty()) {
                                    HyperLinkText(
                                        festivalDetail.homePageUrl,
                                        Icons.Filled.Home
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(vertical = 10.dp, horizontal = 10.dp)
                        ) {
                            Text(
                                text = festivalDetail.overview,
                                color = Color.Black,
                                lineHeight = 25.sp,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }

                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(vertical = 10.dp, horizontal = 10.dp)
                        ) {
                            Text(
                                text = festivalDetail.infoText,
                                lineHeight = 25.sp,
                                color = Color.Black,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                    }

                }

            }) { innerPadding ->
            Box(
                Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                RollingBanner( modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                    festivalDetail.images)
            }
        }

    }

    if (festivalDetailState.isLoading()) {
        LoadingWidget()
    }
}