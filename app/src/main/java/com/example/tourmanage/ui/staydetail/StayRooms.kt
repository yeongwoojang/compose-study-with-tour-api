package com.example.tourmanage.ui.staydetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.extension.convertKRW
import com.example.tourmanage.common.extension.isBooleanYn
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.getOptionString
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayRooms(optionItem: DetailItem, index: Int, size: Int, paddingModifier: Modifier) {
    Timber.i("RoomsInfo()")

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Text(
            modifier = paddingModifier,
            text = optionItem.getOptionString(),
            fontSize = 11.sp,
            color = colorResource(id = R.color.black)
        )

        Row(
            modifier = paddingModifier.padding(bottom = 15.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(CornerSize(16.dp)))
                    .clickable {

                    },
                contentScale = ContentScale.Crop,
                model = optionItem.roomImg1,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier
                .height(150.dp),
                verticalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        text = optionItem.roomTitle.isEmptyString(),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.black)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "기준 인원: ${optionItem.roomBaseCount}명",
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "최대 인원: ${optionItem.roomMaxCount}명",
                        fontSize = 11.sp
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${optionItem.offWeekDayFee.isEmptyString("0").convertKRW()}원",
                    textAlign = TextAlign.End,
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

//    if (index != size - 1) {
//        Spacer(modifier = Modifier.height(10.dp))
//        Divider(
//            modifier = Modifier.fillMaxWidth(),
//            color = colorResource(id = R.color.white_smoke),
//            thickness = 8.dp,)
//    }
}