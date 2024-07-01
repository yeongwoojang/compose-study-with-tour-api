package com.example.tourmanage.ui.home

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun RollingBanner(
    modifier: Modifier = Modifier,
    itemList: List<Any> = emptyList()
) {

    val festivalItems = if (itemList.all { it is FestivalItem }) {
        itemList as ArrayList<FestivalItem>  // 안전하게 캐스팅
    } else {
        emptyList<FestivalItem>()
    }
    val pagerState = rememberPagerState(pageCount = { festivalItems.size })

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
                    if (pagerState.currentPage + 1 in 0..festivalItems.size) {
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
            HorizontalPager(
                state = pagerState
            ) { index ->
                festivalItems.getOrNull(index % (festivalItems.size))?.let { item ->
                    Box {
                        GlideImage(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            model = item.mainImage, contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                                .align(Alignment.TopStart),
                            color = Color.White,
                            text = item.title.isEmptyString(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = spoqaHanSansNeoFont,
                                fontWeight = FontWeight.Medium,
                            ),
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .align(alignment = Alignment.BottomCenter),
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

            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 10.dp)
                .background(
                    color = colorResource(
                        id = R.color.black_4d
                    ), shape = RoundedCornerShape(12.dp)
                )
                .width(80.dp)
                .height(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "더 알아보기",
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.white_smoke)
                    ),
                )
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