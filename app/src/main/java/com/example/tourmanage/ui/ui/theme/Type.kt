package com.example.tourmanage.ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.tourmanage.R

val spoqaHanSansNeoFont = FontFamily(
    Font(R.font.spoqa_han_sans_neo_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_thin, FontWeight.Thin, FontStyle.Normal),
)
private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = spoqaHanSansNeoFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = spoqaHanSansNeoFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = spoqaHanSansNeoFont),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = spoqaHanSansNeoFont),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = spoqaHanSansNeoFont),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = spoqaHanSansNeoFont),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = spoqaHanSansNeoFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = spoqaHanSansNeoFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = spoqaHanSansNeoFont),
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = spoqaHanSansNeoFont),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = spoqaHanSansNeoFont),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = spoqaHanSansNeoFont),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = spoqaHanSansNeoFont),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = spoqaHanSansNeoFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = spoqaHanSansNeoFont),

)