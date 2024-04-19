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
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(modifier = Modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xff373737) else Color(0xff373737))
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

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = scrollState
    ) {
        itemsIndexed(festivalItems) {index, item ->
            Card(
                modifier = Modifier
                    .width(screenWidth)
                    .height(120.dp)
                    .padding(start = 16.dp, end = 16.dp)
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
        modifier = Modifier.fillMaxWidth(),
        pageCount = festivalItems.size,
        currentPage = curIdx
    )

    if (scrollState.isScrollInProgress) {
        isAnimating = true
        Timber.i("Scroll isAnimating")
    } else {
        isAnimating = false
        curIdx = scrollState.firstVisibleItemIndex
        Timber.i("Scroll end")
    }

    LaunchedEffect(curIdx) {
        Timber.i("curIdx: $curIdx")
        while (true) {
            delay(1000L)
            if (!isAnimating) {
                val nextIndex = (curIdx + 1) % festivalItems.size
                scrollState.animateScrollToItem(nextIndex)
                curIdx = nextIndex
            }
        }
    }
}