package com.example.tourmanage.ui.festival

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.FestivalViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FestivalBanner(viewModel: FestivalViewModel = hiltViewModel(), festivalItems: ArrayList<FestivalItem>) {
    var imageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(3000)
            imageIndex = (imageIndex + 1) % festivalItems.size
        }
    }

    Scaffold(
        topBar = { Header(menuName = "Festival") },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 70.dp)) {
            item {
                Text(text = "이번 달 진행중인 축제",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    ))
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(end = 16.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()) {//_ box1
                        Crossfade(
                            targetState = imageIndex,
                            animationSpec = tween(1000)
                        ) {targetState ->
                            Box {
                                GlideImage(
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    model = festivalItems[targetState].mainImage,
                                    contentDescription = "")
                            }
                            Text(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(start = 10.dp, top = 10.dp)
                                    .shadow(10.dp, shape = RoundedCornerShape(8.dp)),
                                text = festivalItems[targetState].title.isEmptyString(),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White
                                )
                            )
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
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .padding(end = 16.dp)
                    .background(color = colorResource(id = R.color.white_smoke))
                )
                Spacer(modifier = Modifier.height(20.dp))
                MyLocationFestival()
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .padding(end = 16.dp)
                    .background(color = colorResource(id = R.color.white_smoke))
                )
                Spacer(modifier = Modifier.height(20.dp))
                OtherLocationFestival()
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
                    .padding(end = 16.dp)
                    .background(color = colorResource(id = R.color.white_smoke))
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}