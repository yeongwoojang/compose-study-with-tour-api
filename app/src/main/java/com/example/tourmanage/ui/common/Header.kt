package com.example.tourmanage.ui.common

import android.app.Activity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun Header(menuName: String) {
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = Color.White,
        title = {
            Text(
                text = menuName,
                style = TextStyle(
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                (context as? Activity)?.finish()
            }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Close")
            }
        }
    )
}