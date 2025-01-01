package com.example.tourmanage.presenter.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    currentMenu:  Config.CONTENT_TYPE_ID,
    onClickMenu: (Config.CONTENT_TYPE_ID) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = DataProvider.homeMenuList.size,
            key = { index ->
                DataProvider.homeMenuList[index].type
            }
        ) { index ->
            val item = DataProvider.homeMenuList[index]

            val menuColor = if (currentMenu == item.type) {
                colorResource(R.color.white_smoke)
            } else {
                Color.Black
            }
            val menuTextColor = if (currentMenu == item.type) {
                Color.Black
            } else {
                Color.White
            }
            Box(
                modifier = Modifier
                    .background(
                        color = menuColor,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(15.dp)
                    .clickable {
                        onClickMenu(item.type)
                    }
            ) {

                Text(
                    text = item.title,
                    color = menuTextColor,
                    style = TextStyle(
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}