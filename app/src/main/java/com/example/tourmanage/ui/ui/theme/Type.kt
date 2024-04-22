package com.example.tourmanage.ui.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R

val spoqaHanSansNeoFont = FontFamily(
    Font(R.font.spoqa_han_sans_neo_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.spoqa_han_sans_neo_thin, FontWeight.Thin, FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)