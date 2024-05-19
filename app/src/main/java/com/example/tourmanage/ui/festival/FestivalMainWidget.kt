package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.FestivalViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(viewModel: FestivalViewModel = hiltViewModel(), navController: NavHostController, mainFestival: ArrayList<FestivalItem>) {
    LaunchedEffect(Unit) {
        if (!viewModel.currentInitState()) {
            viewModel.requestAreaFestival()
            viewModel.requestMyLocationInfo(Config.CONTENT_TYPE_ID.FESTIVAL)
            viewModel.initState()
        }
    }

    val areaFestival = viewModel.areaFestival.collectAsStateWithLifecycle()
    val myLocFestival = viewModel.myLocFestival.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { Header(menuName = "Festival") },
    ) {
        if (areaFestival.isSuccess() && myLocFestival.isSuccess()) {
            val areaFestivalData = areaFestival.value.data!!
            val myLocFestivalData = myLocFestival.value.data!!
            FestivalContents(it, navController, mainFestival, areaFestivalData, myLocFestivalData)
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
        item {
            MyLocationFestival(myLocFestival = myLocFestival)
        }
        item {
            RecommendBanner(areaFestival = areaFestival)
        }
    }
}