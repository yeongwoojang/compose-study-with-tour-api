package com.example.tourmanage.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.isEmptyString
import kotlinx.coroutines.delay
import androidx.compose.material.Divider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.tourmanage.R
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = 3.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean) {
    val size = animateDpAsState(targetValue = if (isSelected) 8.dp else 6.dp, label = "")
    Box(modifier = Modifier
        .size(size.value)
        .clip(CircleShape)
        .background(Color.Black)
    ) {

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainImageRow(festivalItems: ArrayList<FestivalItem>) {
    var curIdx by remember { mutableStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val scrollState = rememberLazyListState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column() {
        Row(
            modifier = Modifier.padding(start = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이달의 축제",
                style = TextStyle(
                    color = colorResource(id = R.color.cornflower_blue),
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Divider(
                Modifier
                    .weight(2f)
                    .height(1.5.dp)
                    .padding(end = 20.dp)
                    .background(colorResource(id = R.color.white_smoke)))
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = scrollState
        ) {
            itemsIndexed(festivalItems) {index, item ->
                Card(
                    modifier = Modifier
                        .width(screenWidth)
                        .height(120.dp)
                        .padding(start = 16.dp, end = 16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box {
                        GlideImage(
                            contentScale = ContentScale.Crop,
                            model = item.mainImage,
                            contentDescription = null,
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                                .shadow(10.dp, shape = RoundedCornerShape(8.dp)),
                        ) {
                            Text(
                                color = Color.White,
                                text = item.title.isEmptyString(),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
        PageIndicator(
            pageCount = festivalItems.size,
            currentPage = curIdx
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .padding(start = 25.dp, end = 20.dp)
                .background(colorResource(id = R.color.white_smoke)))
    }

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress) {
            isAnimating = true
        } else {
            isAnimating = false
            curIdx = scrollState.firstVisibleItemIndex
        }
    }

    LaunchedEffect(curIdx) {
        while (true) {
            delay(2000L)
            if (!isAnimating) {
                val nextIndex = (curIdx + 1) % festivalItems.size
                scrollState.animateScrollToItem(nextIndex)
                curIdx = nextIndex
            }
        }
    }
}