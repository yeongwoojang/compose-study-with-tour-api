package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.FestivalNavItem
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.FestivalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(
    viewModel: FestivalViewModel,
    mainFestival: ArrayList<FestivalItem>,
    moveToDetail: (String) -> Unit = {},
    choiceFestival: (Any) -> Unit = {},
) {
    LaunchedEffect(Unit) {
        viewModel.goDetailFlow.collect {
            moveToDetail("${FestivalNavItem.Detail.route}/${it.title}")
        }
    }

    val festivalInfo = viewModel.festivalInfo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            val context = LocalContext.current
            Header(menuName = "Festival", Config.HEADER_BUTTON_TYPE.HOME) {
                (context as Activity).finish()
            }
        }
    ) {
        if (festivalInfo.isSuccess()) {
            val festivalData = festivalInfo.value.data!!
            val recommendFestival = festivalData.recommendFestival
            val locFestival = festivalData.localFestival

            FestivalContents(
                paddingValues = it,
                mainFestival = mainFestival,
                areaFestival = recommendFestival,
                myLocFestival = locFestival,
                choiceFestival = choiceFestival
            )
        }
    }

    if (festivalInfo.isLoading()) {
        LoadingWidget()
    }
}

@Composable
fun FestivalContents(
    paddingValues: PaddingValues,
    mainFestival: ArrayList<FestivalItem>,
    areaFestival: ArrayList<FestivalItem>,
    myLocFestival: ArrayList<LocationBasedItem>,
    choiceFestival: (Any) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = paddingValues.calculateTopPadding() + 20.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            FestivalBanner(
                mainFestival = mainFestival,
                choiceFestival = choiceFestival
            )
        }
        if (myLocFestival.isNotEmpty()) {
            item {
                MyLocationFestival(
                    myLocFestival = myLocFestival,
                    choiceFestival = choiceFestival
                )
            }
        }
        item {
            RecommendBanner(
                areaFestival = areaFestival,
                choiceFestival = choiceFestival
            )
        }
    }
}