package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.UiState
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.home.RollingBanner
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.FestivalDetail
import com.example.tourmanage.viewmodel.FestivalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalDetailScreen(
    viewModel: FestivalViewModel,
    festivalDetailState: State<UiState<FestivalDetail>>
) {
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
                                    FestivalDetailDescRow(festivalDetail.addr1, Icons.Filled.LocationOn)
                                }
                                if (festivalDetail.tel.isNotEmpty()) {
                                    FestivalDetailDescRow(festivalDetail.tel, Icons.Filled.Call)
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