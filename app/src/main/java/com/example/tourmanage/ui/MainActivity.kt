package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tourmanage.R
import com.example.tourmanage.ui.home.BottomContainer
import com.example.tourmanage.ui.home.CenterCardContainer
import com.example.tourmanage.ui.home.TopContainer
import com.example.tourmanage.ui.navigator.BottomNav
import com.example.tourmanage.ui.navigator.NavigationGraph
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel by viewModels<MainViewModel>()
        setContent {
            TourManageTheme {
                Surface(color = MaterialTheme.colors.background) {
//                    Main()
                    MainScreenView()
                }
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(
                Modifier.height(60.dp),
                containerColor = colorResource(id = R.color.white_smoke),
                contentColor = Color.White,
                indicatorColor = Color.Green,
                navController = navController
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationGraph(navController)
        }
    }
}

@Composable
fun Main() {
    Box(modifier = Modifier.fillMaxSize()) {
        TopContainer()
        BottomContainer(modifier = Modifier.align(Alignment.BottomEnd))
        CenterCardContainer(LocalContext.current)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TourManageTheme {
        TopContainer()
    }
}