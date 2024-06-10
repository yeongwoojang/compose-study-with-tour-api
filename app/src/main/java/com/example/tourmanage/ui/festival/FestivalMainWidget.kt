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
import com.example.tourmanage.data.DetailNavItem
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.FestivalViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(viewModel: FestivalViewModel = hiltViewModel(), navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo(Config.CONTENT_TYPE_ID.FESTIVAL)
        viewModel.goDetailFlow.collect {
            navController.navigate("${DetailNavItem.Detail.route}/${it.title}")
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

            FestivalContents(viewModel, it, navController, mainFestival, recommendFestival, locFestival)
        }
    }

    if (festivalInfo.isLoading()) {
        LoadingWidget()
    }
}

@Composable
fun FestivalContents(viewModel: FestivalViewModel = hiltViewModel(), paddingValues: PaddingValues, navController: NavHostController, mainFestival: ArrayList<FestivalItem>, areaFestival: ArrayList<FestivalItem>, myLocFestival: ArrayList<LocationBasedItem>) {
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
            FestivalBanner(mainFestival = mainFestival) {
                viewModel.choiceFestival(it)
            }
        }
        if (myLocFestival.isNotEmpty()) {
            item {
                MyLocationFestival(myLocFestival = myLocFestival) {
                    viewModel.choiceFestival(it)

                }
            }
        }
        item {
            RecommendBanner(areaFestival = areaFestival) {
                viewModel.choiceFestival(it)
            }
        }
    }
}