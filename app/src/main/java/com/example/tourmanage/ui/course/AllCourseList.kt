package com.example.tourmanage.ui.course

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun AllCourseList() {
    Text(
        text = "한국 전국을 아우르는 다양한 여행 코스를 소개합니다.",
        style = TextStyle(
            fontSize= 14.sp,
            fontFamily = spoqaHanSansNeoFont,
            fontWeight = FontWeight.Normal
        )
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = "한국 곳곳의 신비로운 여행 코스를 탐험해보세요.",
        style = TextStyle(
            color = colorResource(id = R.color.dark_gray),
            fontSize= 11.sp,
            fontFamily = spoqaHanSansNeoFont,
            fontWeight = FontWeight.Thin
        )
    )
}