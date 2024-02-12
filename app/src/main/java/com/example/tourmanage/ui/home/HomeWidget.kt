package com.example.tourmanage.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.MainApplication
import com.example.tourmanage.R
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.MainActivity3
import com.example.tourmanage.ui.ui.theme.OceanBlue
import timber.log.Timber

@Composable
fun TopContainer() {
    Column(modifier = Modifier
        .background(OceanBlue)
        .fillMaxHeight(0.35f)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "가나다라마바사", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.baseline_settings_white_36),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        Timber.d("설정 버튼 클릭")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(modifier = Modifier.padding(start = 20.dp),
            text = "가나다라마바사\n아자차카타파하",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CenterCardContainer(context: Context) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 150.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CenterBox(context, Config.CARD_TYPE.TYPE_A)
            CenterBox(context, Config.CARD_TYPE.TYPE_B)
        }
    }
}

@Composable
fun CenterBox(context: Context, type: Config.CARD_TYPE) {
    var boxColor = Color.White
    var title = ""
    var desc = ""
    when (type) {
        Config.CARD_TYPE.TYPE_A -> {
            boxColor = Color.White
            title = "TYPE A"
            desc = "This is Type A"
        }
        Config.CARD_TYPE.TYPE_B -> {
            boxColor = Color.Blue
            title = "TYPE B"
            desc = "This is Type B"
        }
        else -> {
            boxColor = Color.White
        }
    }
    Column(modifier = Modifier
        .background(color = boxColor, shape = RoundedCornerShape(25.dp))
        .size(width = 180.dp, height = 200.dp)
        .clickable {
            UiController.addActivity(context, MainActivity3::class, IntentData(
                mapOf(Config.MAIN_MENU_KEY to type.name)
            ))
        }
        .padding(start = 10.dp, end = 10.dp)) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = desc)
    }
}

@Composable
fun BottomContainer(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.7f)
        .background(
            color = Color.Yellow,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        )
    ) {

    }
}