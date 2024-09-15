package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.FestivalViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainScreen(
    viewModel: FestivalViewModel = hiltViewModel(),
    mainFestival: ArrayList<FestivalItem>,
    choiceFestival: (String) -> Unit = {},
    onDismissFestivalPage: () -> Unit
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

        if (festivalInfo.isSuccess()) {
        val festivalData = festivalInfo.value.data!!
        val recommendFestival = festivalData.recommendFestival
        val locFestival = festivalData.localFestival
        FestivalContents(
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
            },
            onDismiss = onDismissFestivalPage
        )
    }
    if (festivalInfo.isLoading()) {
        LoadingWidget()
    }
}