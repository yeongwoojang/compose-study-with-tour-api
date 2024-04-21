package com.example.tourmanage.ui.wish

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.tourmanage.R

@Composable
fun WishWidget() {
    var curIndex by remember { mutableStateOf(0) }
    val tab = listOf("1", "2", "3")
    TabRow(
        selectedTabIndex = curIndex,
        contentColor = colorResource(id = R.color.black),
        containerColor = colorResource(id = R.color.white_smoke),
        indicator = {
        }) {
        tab.forEachIndexed { index, tab ->
            Tab(
                modifier = Modifier.height(40.dp),
                selected = curIndex == index,
                onClick = {
                    curIndex = index
                })
            {
                Text(text = "$tab")

            }
        }
    }
}