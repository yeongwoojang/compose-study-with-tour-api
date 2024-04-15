package com.example.tourmanage.ui.navigator

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tourmanage.data.BottomNavItem

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    indicatorColor: Color,
    navController: NavHostController
) {
    val navBacStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBacStackEntry?.destination?.route
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Wish,
        BottomNavItem.Area,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Thin,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                },
                icon = {
                    val isSelected = currentRoute == item.route
                  Icon(
                      painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
                      contentDescription = stringResource(id = item.title)
                  )
                },
                onClick = {
                     navController.navigate(item.route) {
                         navController.graph.startDestinationRoute?.let {
                             popUpTo(it) { saveState = true}
                         }
                         launchSingleTop = true
                         restoreState = true
                     }
                }
            )
        }

    }
}