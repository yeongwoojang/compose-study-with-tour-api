package com.example.tourmanage.ui.course

import android.location.Address
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.toAreaText
import com.example.tourmanage.common.extension.toMyAreaText
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyCourseList(courseItems: ArrayList<LocationBasedItem>) {
    val context = LocalContext.current
    var myAreaAddress by remember { mutableStateOf<Address?>(null) }
    val currentGps = ServerGlobal.getCurrentGPS()
    LaunchedEffect(key1 = Unit) {
        myAreaAddress = ServerGlobal.getAddress(context, currentGps.mapY.isEmptyString("0").toDouble(), currentGps.mapX.isEmptyString("0").toDouble())

    }

    if (myAreaAddress != null) {
        Text(
            text = "${myAreaAddress!!.subLocality}의 여행 코스",
            style = TextStyle(
                fontSize= 14.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(
                items = courseItems,
                key = { index, courseItem ->
                    courseItem.contentId.isEmptyString()
                }
            ) { index, courseItem ->
                val isLastItem = courseItem == courseItems.last()
                val padding = if (isLastItem) 16.dp else 0.dp

                Column(modifier = Modifier
                    .width(200.dp)
                    .padding(end = padding)) {
                    GlideImage(
                        model = courseItem.mainImage,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = courseItem.title.isEmptyString(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily =  spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                }
            }
        }

    }
}