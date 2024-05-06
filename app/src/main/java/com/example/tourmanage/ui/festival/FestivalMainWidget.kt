package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.FestivalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalMainWidget(viewModel: FestivalViewModel = hiltViewModel(), mainFestival: ArrayList<FestivalItem>) {
    LaunchedEffect(Unit) {
        viewModel.requestAreaFestival()
        viewModel.requestMyLocationFestival()
    }

    val areaFestival = viewModel.areaFestival.collectAsStateWithLifecycle()
    val myLocFestival = viewModel.myLocFestival.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { Header(menuName = "Festival") },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = it.calculateTopPadding() + 20.dp, bottom = it.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
               FestivalBanner(mainFestival = mainFestival)
            }
            if (myLocFestival.isSuccess()) {
                item {
                    MyLocationFestival(myLocFestival = myLocFestival.value.data!!)
                }
            }
            if (areaFestival.isSuccess()) {
                item {
                    RecommendBanner(areaFestival = areaFestival.value.data!!)
                }
            }
        }
    }
}