package com.example.tourmanage.ui.festival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem

@Composable
fun FestivalContents(
    paddingValues: PaddingValues,
    mainFestival: ArrayList<FestivalItem>,
    areaFestival: ArrayList<FestivalItem>,
    myLocFestival: ArrayList<LocationBasedItem>,
    choiceFestival: (String) -> Unit
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