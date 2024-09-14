package com.example.tourmanage.ui.festival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem

@Composable
fun FestivalContents(
    mainFestival: ArrayList<FestivalItem>,
    areaFestival: ArrayList<FestivalItem>,
    myLocFestival: ArrayList<LocationBasedItem>,
    favorList: List<FavorEntity>,
    choiceFestival: (String) -> Unit,
    requestAddFavor: (String, String, String, String) -> Unit,
    requestDelFavor: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sections = mutableListOf<@Composable () -> Unit>()

    sections.add {
        FestivalBanner(
            mainFestival = mainFestival,
            favorList = favorList,
            choiceFestival = choiceFestival,
        )
    }

    if (myLocFestival.isNotEmpty()) {
        sections.add {
            MyLocationFestival(
                myLocFestival = myLocFestival,
                favorList = favorList,
                choiceFestival = choiceFestival,
                requestAddFavor = requestAddFavor,
                requestDelFavor = requestDelFavor,
            )
        }
    }

    sections.add {
        RecommendBanner(
            areaFestival = areaFestival,
            favorList = favorList,
            choiceFestival = choiceFestival,
        )
    }

    Scaffold(
        topBar = {
            FestivalHeader(
                onDismiss = onDismiss
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(sections) { section ->
                section()
            }
        }
    }

}