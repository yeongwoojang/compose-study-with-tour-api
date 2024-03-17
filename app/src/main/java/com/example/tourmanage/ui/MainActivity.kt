package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tourmanage.ui.home.BottomContainer
import com.example.tourmanage.ui.home.CenterCardContainer
import com.example.tourmanage.ui.home.TopContainer
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
                    Main()
                }
            }
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