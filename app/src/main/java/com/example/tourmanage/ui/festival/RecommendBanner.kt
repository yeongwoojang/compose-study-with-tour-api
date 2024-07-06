package com.example.tourmanage.ui.festival

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.FestivalViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecommendBanner(areaFestival: ArrayList<FestivalItem>, choiceFestival: (String)-> Unit) {
    var imageIndex by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            imageIndex = (imageIndex + 1) % areaFestival.size
        }
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(end = 16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()) {//_ box1
                Crossfade(
                    targetState = imageIndex,
                    animationSpec = tween(1000),
                ) {targetState ->
                    Box(modifier = Modifier
                        .clickable { choiceFestival(areaFestival[targetState].contentId.orEmpty()) }) {
                        GlideImage(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            model = areaFestival[targetState].mainImage,
                            contentDescription = "")
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
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.white_smoke)
                            ),
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(start = 2.dp, end = 18.dp),
            text = "부산의 다채로운 축제를 만나보세요!",
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = spoqaHanSansNeoFont
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 2.dp, end = 18.dp),
            text = "부산에서는 매년 다채로운 행사와 축제가 열립니다.\n해변가에서의 불꽃놀이부터 문화 축제까지, 특별한 순간을 경험하세요!",
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = spoqaHanSansNeoFont,
                color = colorResource(id = R.color.black_4d)
            )
        )
    }


}