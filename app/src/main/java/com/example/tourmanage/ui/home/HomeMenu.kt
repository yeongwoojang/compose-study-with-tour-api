package com.example.tourmanage.ui.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.FestivalItem
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.CourseActivity
import com.example.tourmanage.ui.FestivalMainActivity
import com.example.tourmanage.ui.LocalTourActivity
import com.example.tourmanage.ui.StayMainActivity
import kotlin.reflect.KClass

@Composable
fun HomeMenu(festivalItems: ArrayList<FestivalItem>, curParentArea: AreaItem?, curChildArea: AreaItem?) {
    val context = LocalContext.current
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 25.dp, top = 20.dp, bottom = 20.dp, end = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = DataProvider.homeMenuList,
            itemContent = {
                Card(
                    shape = RoundedCornerShape(CornerSize(8.dp)),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.size(150.dp)
                        .clickable {
                            var intentData: IntentData? = null
                            val targetActivity: KClass<out Activity> = when (it.type) {
                                Config.HOME_MENU_TYPE.FESTIVAL -> {
                                    intentData = IntentData(
                                        mapOf(Config.PASS_DATA.DATA.value to festivalItems)
                                    )
                                    FestivalMainActivity::class
                                }
                                Config.HOME_MENU_TYPE.STAY -> {
                                    intentData = IntentData(
                                        mapOf(
                                            Config.PASS_DATA.PARENT_AREA.value to curParentArea,
                                            Config.PASS_DATA.CHILD_AREA.value to curChildArea)
                                    )
                                    StayMainActivity::class
                                }
                                Config.HOME_MENU_TYPE.TOUR_SPOT -> {
                                    intentData = IntentData(
                                        mapOf(Config.PASS_DATA.DATA.value to festivalItems)
                                    )
                                    LocalTourActivity::class
                                }
                                Config.HOME_MENU_TYPE.COURSE -> {
                                    intentData = IntentData(
                                        mapOf(
                                            Config.PASS_DATA.PARENT_AREA.value to curParentArea,
                                            Config.PASS_DATA.CHILD_AREA.value to curChildArea)
                                    )
                                    CourseActivity::class
                                }
//                                Config.HOME_MENU_TYPE.RIDING ->
//                                Config.HOME_MENU_TYPE.CULTURE ->
                                else -> FestivalMainActivity::class
                            }
                            UiController.addActivity(context, targetActivity, intentData)
                        }
                    ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = it.image),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
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