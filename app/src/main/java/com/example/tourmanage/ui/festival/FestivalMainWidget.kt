package com.example.tourmanage.ui.festival

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.FestivalViewModel
import kotlinx.coroutines.delay
import timber.log.Timber

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
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(70.dp))
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