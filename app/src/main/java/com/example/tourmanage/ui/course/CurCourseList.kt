package com.example.tourmanage.ui.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurCourseList(courseItems: ArrayList<AreaBasedItem>, curParentArea: AreaItem?, curChildArea: AreaItem?) {
    Text(
        text = "${curParentArea?.name} ${curChildArea?.name}의 여행 코스",
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
        ) {index, courseItem ->
            Column(modifier = Modifier
                .width(150.dp)
            ) {
                GlideImage(
                    model = courseItem.fullImageUrl,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    failure = placeholder(R.drawable.stay_default),
                    loading = placeholder(R.drawable.stay_default),
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