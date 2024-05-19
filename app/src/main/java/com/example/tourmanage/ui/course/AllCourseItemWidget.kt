package com.example.tourmanage.ui.course

import android.annotation.SuppressLint
import android.location.Address
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.toAreaText
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AllCourseItemWidget(allCourseItem: AreaBasedItem) {
    val context = LocalContext.current
    var address by remember { mutableStateOf<Address?>(null) }

    LaunchedEffect(key1 = Unit) {
        address = ServerGlobal.getAddress(context, allCourseItem.mapY.isEmptyString("0").toDouble(), allCourseItem.mapX.isEmptyString("0").toDouble())
    }

    Column{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(end = 16.dp)
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
        ) {
            GlideImage(
                model = allCourseItem.fullImageUrl,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "${allCourseItem.title}",
            style = TextStyle(
                fontSize= 13.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Normal
            )
        )
        if (address != null) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = address!!.toAreaText(),
                style = TextStyle(
                    color = colorResource(id = R.color.dark_gray),
                    fontSize = 11.sp,
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Thin
                )
            )
        }
    }
}