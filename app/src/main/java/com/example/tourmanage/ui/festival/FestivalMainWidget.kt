package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.FestivalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(viewModel: FestivalViewModel = hiltViewModel(), navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo(Config.CONTENT_TYPE_ID.FESTIVAL)
    }

    val festivalInfo = viewModel.festivalInfo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { Header(menuName = "Festival") },
    ) {
        if (festivalInfo.isSuccess()) {
            val festivalData = festivalInfo.value.data!!
            val recommendFestival = festivalData.recommendFestival
            val locFestival = festivalData.localFestival

            FestivalContents(it, navController, mainFestival, recommendFestival, locFestival)
        }
    }
}

@Composable
fun FestivalContents(paddingValues: PaddingValues, navController: NavHostController, mainFestival: ArrayList<FestivalItem>, areaFestival: ArrayList<FestivalItem>, myLocFestival: ArrayList<LocationBasedItem>) {
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
            FestivalBanner(navController = navController, mainFestival = mainFestival)
        }
        if (myLocFestival.isNotEmpty()) {
            item {
                MyLocationFestival(myLocFestival = myLocFestival)
            }
        }
        item {
            RecommendBanner(areaFestival = areaFestival)
        }
    }
}