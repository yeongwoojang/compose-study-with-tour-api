package com.example.tourmanage.presenter.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.presenter.ui.theme.TourManageTheme
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun RollingBanner(
    modifier: Modifier = Modifier,
    itemList: List<Any> = emptyList()
) {
    var type = "BOTTOM"
    val items = if (itemList.all { it is PosterItem }) {
        itemList as ArrayList<PosterItem>  // 안전하게 캐스팅
    } else if (itemList.all { it is DetailImageItem}) {
        type = "TOP"
        itemList as ArrayList<DetailImageItem>  // 안전하게 캐스팅
    } else {
        emptyList()
    }

    val pagerState = rememberPagerState(pageCount = { items.size })

    // 초기페이지 설정. 한번만 실행되기 원하니 key 는 Unit|true.
    LaunchedEffect(key1 = Unit) {
        pagerState.scrollToPage(0)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            while (true) {
                delay(3000L)
                // 페이지 바뀌었다고 애니메이션이 멈추면 어색하니 NonCancellable
                withContext(NonCancellable) {
                    // 일어날린 없지만 유저가 약 10억번 스크롤할지 몰라.. 하는 사람을 위해..
                    if (pagerState.currentPage + 1 in 0..items.size) {
                        pagerState.animateScrollToPage(pagerState.currentPage.inc() % pagerState.pageCount)
                    }
                }
            }
        }
    }


    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            if (items.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState
                ) { index ->
                    val imageItem = items[index]
                    val imageUrl = when (imageItem) {
                        is PosterItem -> imageItem.imgUrl
                        is DetailImageItem -> imageItem.originImgUrl
                        else -> ""
                    }

                    val title = when (imageItem) {
                        is PosterItem -> imageItem.title
                        else -> ""
                    }
                    items.getOrNull(index % (items.size))?.let { item ->
                        Box {
                            GlideImage(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds,
                                model = imageUrl, contentDescription = ""
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "이미지를 불러올 수 없습니다.",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = spoqaHanSansNeoFont,
                        )
                    )
                }
            }

            val alignment = when (type) {
                "BOTTOM" -> Alignment.BottomCenter
                "TOP" -> Alignment.TopCenter
                else -> Alignment.BottomCenter
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .align(alignment = alignment),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color = color)
                            .size(8.dp)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun InfiniteBannerPreview() {
    TourManageTheme {
        RollingBanner()
    }
}