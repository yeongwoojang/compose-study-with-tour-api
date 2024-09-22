package com.example.tourmanage.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import timber.log.Timber

@Composable
fun LoadingWidget(modifier: Modifier = Modifier) {
    Timber.i("LoadingWidget() is called.")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        )
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}