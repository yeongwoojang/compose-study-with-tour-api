package com.example.tourmanage.ui.festival

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.R
import com.example.tourmanage.viewmodel.FestivalViewModel
import kotlinx.coroutines.delay
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing

@Composable
fun FestivalBanner(viewModel: FestivalViewModel = hiltViewModel()) {
    val imageResources = listOf(
        R.drawable.festival_menu,
        R.drawable.walk_menu,
        R.drawable.riding_menu,
    )

    var imageIndex by remember { mutableStateOf(0) }

    val fadeInSpec = remember {
        fadeIn(animationSpec = TweenSpec(1000, easing = LinearEasing))
    }

    val fadeOutSpec = remember {
        fadeOut(animationSpec = TweenSpec(2500, easing = LinearEasing))
    }

    var isVisible by remember{ mutableStateOf(true) }

    var isShowAnim by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = imageIndex, key2 = isVisible) {
        delay(3000)
        isVisible = false
        delay(10)
        imageIndex = (imageIndex + 1) % imageResources.size
        isVisible = true
    }

//    LaunchedEffect(Unit) {
//        delay(2000)
//        isVisible = false
//    }


    Scaffold(
        topBar = { Header(menuName = "Festival") },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 70.dp, end = 25.dp)) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()) {//_ box1
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isVisible,
                            enter = fadeInSpec,
                            exit = fadeOutSpec
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                painter = painterResource(id = imageResources[imageIndex]), contentDescription = ""
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
        }
    }
}