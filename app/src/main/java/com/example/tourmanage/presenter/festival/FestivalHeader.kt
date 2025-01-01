package com.example.tourmanage.presenter.festival

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.presenter.ui.theme.TourManageTheme
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalHeader(onDismiss: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "BACK",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "다양한 축제를 알아보아요.",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        actions = {
            Box(
                modifier = Modifier.size(48.dp) // IconButton 크기와 비슷하게 설정
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "BACK",
                    tint = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    )

}

@Composable
@Preview
fun FestivalHeaderPreview() {
    TourManageTheme {
        FestivalHeader(
            onDismiss = {}
        )
    }
}