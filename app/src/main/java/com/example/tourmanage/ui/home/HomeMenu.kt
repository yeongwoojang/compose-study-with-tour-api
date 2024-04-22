package com.example.tourmanage.ui.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.FestivalMainActivity
import com.example.tourmanage.ui.StayMainActivity
import kotlin.reflect.KClass

@Composable
fun HomeMenu(festivalItems: ArrayList<FestivalItem>) {
    val context = LocalContext.current
    LazyHorizontalGrid(rows = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentPadding = PaddingValues(start = 25.dp, top = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = DataProvider.homeMenuList,
            itemContent = {
                Card(
                    shape = RoundedCornerShape(CornerSize(8.dp)),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.width(150.dp)
                        .clickable {
                            var intentData: IntentData? = null
                            val targetActivity: KClass<out Activity> = when (it.type) {
                                Config.HOME_MENU_TYPE.FESTIVAL -> {
                                    intentData = IntentData(
                                        mapOf(Config.PASS_DATA to festivalItems)
                                    )
                                    FestivalMainActivity::class
                                }
                                Config.HOME_MENU_TYPE.STAY -> {
//                                    intentData = IntentData(
//                                        mapOf
//                                    )
                                    StayMainActivity::class
                                }

//                                Config.HOME_MENU_TYPE.WALK ->
//                                Config.HOME_MENU_TYPE.RIDING ->
//                                Config.HOME_MENU_TYPE.CULTURE ->
//                                Config.HOME_MENU_TYPE.TOUR_SPOT ->
                                else -> FestivalMainActivity::class
                            }
                            UiController.addActivity(context, targetActivity, intentData)
                        }
                    ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = it.image),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = it.title,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            ),
                            modifier = Modifier.shadow(4.dp)
                        )
                    }

                }
            }
        )
    }
}