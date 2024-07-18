package com.example.tourmanage.ui.festival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            tint = Color.Black,
            contentDescription = ""
        )
        Text(
            modifier = textModifier,
            text = text,
            fontSize = 11.sp,
            style = TextStyle(
                fontFamily = spoqaHanSansNeoFont,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}