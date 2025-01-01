package com.example.tourmanage.presenter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    primary = primary,
    onPrimary =  background,
    primaryContainer = primaryContainer,
    onPrimaryContainer = primaryContainer,
    surface = primaryContainer,
    onSurface = Color.White,
    background = primaryContainer,
    onBackground = background,
)

@Composable
fun TourManageTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = ColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}