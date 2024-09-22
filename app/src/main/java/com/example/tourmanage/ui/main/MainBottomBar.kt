package com.example.tourmanage.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import timber.log.Timber

@Composable
fun MainBottomBar(
    navController: NavController,
    currentRoute: PageRoute,
) {
    if (currentRoute in bottomRoutes) {
        MainBottomBar(
            currentRoute = currentRoute,
            onItemClick = { route ->
                if(route != currentRoute) {
                    navController.navigate(route = route.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        this.launchSingleTop = true
                        this.restoreState = true
                    }
                }
            }
        )
    }
}

@Composable
private fun MainBottomBar(
    currentRoute: PageRoute,
    onItemClick: (PageRoute) -> Unit
) {

    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(bottom = 2.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomRoutes.forEach { route ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = { onItemClick(route) }) {
                        Icon(
                            imageVector = route.icon!!,
                            contentDescription = route.contentDescription,
                            tint = if (currentRoute == route) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.White
                            }
                        )
                    }

                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = route.contentDescription,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Thin,
                            color = if (currentRoute == route) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.White
                            }
                        )
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    TourManageTheme {
        MainBottomBar(
            currentRoute = PageRoute.HOME,
            onItemClick = {}
        )
    }
}