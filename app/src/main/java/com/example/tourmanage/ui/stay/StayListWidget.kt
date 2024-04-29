package com.example.tourmanage.ui.stay

import android.util.SparseLongArray
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.extension.isEmptyString

@Composable
fun StayListWidget(stayList: ArrayList<StayItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        itemsIndexed(
            items = stayList,
            key = { key, stayItem ->
                stayItem.contentId.isEmptyString()
            }
        ) { index, stayItem ->
            StayItemWidget(stayItem)
        }
    }
}