package com.example.tourmanage.ui.common

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@Composable
fun Header(menuName: String, type: Config.HEADER_BUTTON_TYPE, click: () -> Unit ) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.height(70.dp),
        backgroundColor = Color.White,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                text = menuName,
                style = TextStyle(
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    click()
                }) {
                when (type) {
                    Config.HEADER_BUTTON_TYPE.HOME -> {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")

                    }
                    Config.HEADER_BUTTON_TYPE.CLOSE -> {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }
        }
    )
}
