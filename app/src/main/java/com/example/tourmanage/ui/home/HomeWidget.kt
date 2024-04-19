package com.example.tourmanage.ui.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.HomeViewModel
import java.time.format.TextStyle

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeWidget(viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo(arrangeType = Config.ARRANGE_TYPE.O)
    }
    
    val festivalItems = viewModel.festivalItem.collectAsStateWithLifecycle()
    if (festivalItems.isSuccess()) {
        val festivalList = festivalItems.value.data!!
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = colorResource(id = R.color.white_smoke),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Home",
                            textAlign = TextAlign.Center
                        )
                    },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.home_intro_title),
                        textAlign = TextAlign.Center,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Monospace
                        ),
                        fontWeight = FontWeight.Thin
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    MainImageRow(festivalList)
                }
            }
        }   
    }
}