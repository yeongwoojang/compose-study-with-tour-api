package com.example.tourmanage.ui.staydetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.ui.StayDetailActivity

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayTopImage(detailData: DetailCommonItem, activity: StayDetailActivity) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)) {
        GlideImage(
            contentScale = ContentScale.Crop,
            model = detailData.mainImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Row(
            Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { activity.finish()}) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}