package com.example.tourmanage.ui.home

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.StayMainActivity
import com.example.tourmanage.ui.MainActivity4
import timber.log.Timber
import kotlin.reflect.KClass

@Composable
fun TopContainer() {
    Column(modifier = Modifier
        .background(color = Color.White)
        .fillMaxHeight(0.35f)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "가나다라마바사", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            color = Color.Black,
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
            CenterBox(context, Config.CARD_TYPE.MENU_STAY)
            CenterBox(context, Config.CARD_TYPE.TYPE_B)
        }
    }
}

@Composable
fun CenterBox(context: Context, type: Config.CARD_TYPE) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val interactionSource = remember { MutableInteractionSource() }
    var boxColor = Color.White
    var title = ""
    var desc = ""
    var targetActivity: KClass<out Activity> = StayMainActivity::class
    when (type) {
        Config.CARD_TYPE.MENU_STAY -> {
            boxColor = Color.White
            title = "숙소 예약"
            desc = "원하는 숙소를 검색하고 예약"
            targetActivity = StayMainActivity::class
        }
        Config.CARD_TYPE.TYPE_B -> {
            boxColor = Color.Blue
            title = "TYPE B"
            desc = "This is Type B"
            targetActivity = MainActivity4::class
        }
        else -> {
            boxColor = Color.White
        }
    }
    Card(shape = RoundedCornerShape(CornerSize(25.dp)),
        elevation = 6.dp) {
            Column(modifier = Modifier
                .background(color = boxColor, shape = RoundedCornerShape(25.dp))
                .size(width = screenWidth * 0.48f, height = 200.dp)
                .padding(start = 10.dp, end = 10.dp)
                .clickable(interactionSource = interactionSource, indication = null) {
                    UiController.addActivity(
                        context, targetActivity, IntentData(
                            mapOf(Config.MAIN_MENU_KEY to type.name)
                        )
                    )
                }) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = title, color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = desc, color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        }
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