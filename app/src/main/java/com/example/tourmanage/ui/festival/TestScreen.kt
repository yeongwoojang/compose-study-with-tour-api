package com.example.tourmanage.ui.festival

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TestScreen() {
    Scaffold(
        bottomBar = {}
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)) {
            Text(text = "asdasdas")

        }
    }
}