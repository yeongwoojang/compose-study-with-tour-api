package com.example.tourmanage.ui

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.data.server.item.StayDetailItem
import com.example.tourmanage.common.extension.intentSerializable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import timber.log.Timber

class StayDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receivedData = intent.intentSerializable(Config.STAY_INFO, StayDetailItem::class.java)
        Timber.i("OnCreate")
        setContent {
            TourManageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLayout(receivedData)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPagerApi::class)
@Composable
fun MainLayout(detailData: StayDetailItem?) {
    Timber.i("MainLayout() | detailData: $detailData")

    if (detailData == null) {
        return
    }

    val context = LocalContext.current
    val activity = context as StayDetailActivity

    Column(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(300.dp)) {
            HorizontalPager(
                count = 2,
                ) {
                GlideImage(
                    contentScale = ContentScale.Crop,
                    model = detailData.mainImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                GlideImage(
                    contentScale = ContentScale.Crop,
                    model = detailData.subImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            Row(
                Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { activity.finish()}) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    TourManageTheme {
        Greeting("Android")
    }
}