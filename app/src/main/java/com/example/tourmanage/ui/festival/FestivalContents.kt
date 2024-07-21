package com.example.tourmanage.ui.festival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config

@Composable
fun FestivalContents(
    paddingValues: PaddingValues,
    mainFestival: ArrayList<FestivalItem>,
    areaFestival: ArrayList<FestivalItem>,
    myLocFestival: ArrayList<LocationBasedItem>,
    favorList: List<FavorEntity>,
    choiceFestival: (String) -> Unit,
    requestAddFavor: (String, String, String, String) -> Unit,
    requestDelFavor: (String) -> Unit
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
                favorList = favorList,
                choiceFestival = choiceFestival,
            )
        }
        if (myLocFestival.isNotEmpty()) {
            item {
                MyLocationFestival(
                    myLocFestival = myLocFestival,
                    favorList = favorList,
                    choiceFestival = choiceFestival,
                    requestAddFavor = requestAddFavor,
                    requestDelFavor = requestDelFavor,
                )
            }
        }
        item {
            RecommendBanner(
                areaFestival = areaFestival,
                favorList = favorList,
                choiceFestival = choiceFestival,
            )
        }
    }
}