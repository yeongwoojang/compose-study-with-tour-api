package com.example.tourmanage.presenter.festival

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Test2Creen(
    onClick: () -> Unit,
    ) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)) {
        Button(onClick = {
            onClick()
        }) {

        }
    }
}