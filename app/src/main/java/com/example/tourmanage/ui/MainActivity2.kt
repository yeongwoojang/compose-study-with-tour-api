package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.ui.ui.theme.OceanBlue
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import timber.log.Timber

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourManageTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    Box(modifier = Modifier.fillMaxSize()) {
        TopContainer()
//        BottomContainer()
    }
}

@Composable
fun TopContainer() {
    Column(modifier = Modifier
        .background(OceanBlue)
        .height(250.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "TourManager", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row() {
                Image(
                    painter = painterResource(id = com.example.tourmanage.R.drawable.baseline_settings_white_36),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        Timber.d("설정 버튼 클릭")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(modifier = Modifier.padding(start = 20.dp),
            text = "오픈 채팅방을\n만들어 볼래요?",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomContainer() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)
        .background(Color.Yellow),
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TourManageTheme {
        TopContainer()
    }
}