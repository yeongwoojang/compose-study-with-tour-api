package com.example.tourmanage.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    currentRoute: PageRoute,
    menuClick: () -> Unit = {}
) {
    if (currentRoute in bottomRoutes) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Text(
                    text = currentRoute.contentDescription,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontFamily = spoqaHanSansNeoFont,
                        fontWeight = FontWeight.Medium
                    )
                )
            },
            actions = {
                if (currentRoute == PageRoute.HOME) {
                    Text(
                        text = "지역 변경",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    IconButton(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = menuClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "지역 설정"
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun MainTopBarPreview() {
    TourManageTheme {
        MainTopBar(PageRoute.HOME)
    }
}