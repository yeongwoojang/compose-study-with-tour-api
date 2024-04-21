package com.example.tourmanage.ui.festival

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R

@Composable
fun OtherLocationFestival() {
    Column() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
                .height(55.dp),
            colors = CardDefaults.cardColors(colorResource(id = R.color.white_smoke)),
            elevation = CardDefaults.cardElevation(6.dp),
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "다른 지역 축제 알아보기",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.black_4d)
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "원하는 지역을 선택해주세요.",
                    style = TextStyle(
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light         ,
                        color = colorResource(id = R.color.salmon)
                    )
                )
            }
        }
    }
}