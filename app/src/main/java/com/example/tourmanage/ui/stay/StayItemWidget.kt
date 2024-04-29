package com.example.tourmanage.ui.stay

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayItemWidget(stayItem: StayItem) {
    Card(
        modifier = Modifier
        .fillMaxWidth()
        .height(120.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.white_smoke)),
        elevation = CardDefaults.cardElevation(3.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                model = stayItem.fullImageUrl,
                contentScale = ContentScale.FillBounds,
                failure = placeholder(R.drawable.stay_default),
                loading = placeholder(R.drawable.stay_default),
                contentDescription = "",
            )
            Column {
                Text(
                    text = stayItem.title.isEmptyString(),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stayItem.addr1.isEmptyString(),
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Thin
                    )
                )
            }
        }
    }
}