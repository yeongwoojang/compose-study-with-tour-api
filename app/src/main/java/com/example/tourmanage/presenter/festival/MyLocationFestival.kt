package com.example.tourmanage.presenter.festival

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.R
import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyLocationFestival(
    myLocFestival: ArrayList<LocationBasedItem>,
    favorList: List<FavorEntity>,
    choiceFestival: (String)-> Unit,
    requestAddFavor: (String, String, String, String) -> Unit,
    requestDelFavor: (String) -> Unit
    )
{
    Column {
        Text(
            text = "우리 동네에서 열리는 축제",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = spoqaHanSansNeoFont,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            itemsIndexed(myLocFestival) { index, item ->
                val isFavor = favorList.find { it.contentId == item.contentId } != null
                Timber.i("ITEM: ${item.title} | INDEX: $index | isFavor: $isFavor")
                val favorTint = if (isFavor) Color.Yellow else Color.White
                Column(modifier = Modifier
                    .width(150.dp)
                    .clickable { choiceFestival(item.contentId.orEmpty()) }
                ) {
                    Box {
                        GlideImage(
                            model = item.mainImage,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            tint = favorTint,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    if (isFavor) {
                                        requestDelFavor(item.contentId!!)
                                    } else {
                                        requestAddFavor(
                                            item.contentTypeId!!,
                                            item.contentId!!,
                                            item.title!!,
                                            item.mainImage!!
                                        )
                                    }
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.title.isEmptyString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily =  spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = item.addr1.isEmptyString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 8.sp,
                            fontFamily =  spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.dark_gray)
                        )
                    )
                }
            }
        }
    }
}