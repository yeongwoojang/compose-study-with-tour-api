package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.FestivalNavItem
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.FestivalArgument
import com.example.tourmanage.viewmodel.FestivalViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(
    viewModel: FestivalViewModel,
    mainFestival: ArrayList<FestivalItem>,
    choiceFestival: (String) -> Unit = {},
) {
    val festivalInfo = viewModel.festivalInfo.collectAsStateWithLifecycle()
    var favorList by remember { mutableStateOf<List<FavorEntity>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            viewModel.festivalFavorListFlow.collect {
                favorList = it
            }
        }

        launch {
            viewModel.addFavorErrorFlow.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                favorList = favorList,
                choiceFestival = choiceFestival,
                requestAddFavor = { contentTypeId, contentId, title, image ->
                    viewModel.requestAddFavor(contentTypeId, contentId, title, image)
                },
                requestDelFavor = { contentId ->
                    viewModel.requestDelFavor(contentId)
                }
            )
        }
    }

    if (festivalInfo.isLoading()) {
        LoadingWidget()
    }
}