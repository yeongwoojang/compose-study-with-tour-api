package com.example.tourmanage.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.R
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.HomeViewModel

@Composable
fun HomeWidget(viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo(arrangeType = Config.ARRANGE_TYPE.O)
    }
    
    val festivalItems = viewModel.festivalItem.collectAsStateWithLifecycle()
    if (festivalItems.isSuccess()) {
        val festivalList = festivalItems.value.data!!
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = colorResource(id = R.color.white_smoke),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Home",
                            textAlign = TextAlign.Center
                        )
                    },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
            ) {
                item {
                    MainImageRow(festivalList)
                }
                item {
                    HomeMenu()
                }
            }
        }   
    }

    if (festivalItems.isLoading()) {
        LoadingWidget()
    }
}