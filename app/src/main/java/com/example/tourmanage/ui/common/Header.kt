package com.example.tourmanage.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(menuName: String, type: Config.HEADER_BUTTON_TYPE, click: () -> Unit ) {
    TopAppBar(
        modifier = Modifier.height(70.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary),
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
