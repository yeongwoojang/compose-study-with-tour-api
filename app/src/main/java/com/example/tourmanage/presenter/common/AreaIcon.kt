package com.example.tourmanage.presenter.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.noRippleClickable
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont

@Composable
fun AreaIcon(
    modifier: Modifier = Modifier,
    areaItem: AreaItem,
    color: Long,
    onClick: (selectedItem: AreaItem) -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(color = Color(color), shape = RoundedCornerShape(6.dp))
            .padding(10.dp)
            .noRippleClickable {
                onClick(areaItem)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = areaItem.name.isEmptyString(),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
    }
}