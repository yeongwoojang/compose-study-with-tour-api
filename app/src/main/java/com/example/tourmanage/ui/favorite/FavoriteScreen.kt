package com.example.tourmanage.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.FavorViewModel
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteScreen(viewModel: FavorViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.getFavorAll()
    }

    val favorList = viewModel.favorDataFlow.collectAsStateWithLifecycle()

    if (favorList.isSuccess()) {
        val data = favorList.value.data!!

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
            items(
                count = data.size,
                key = { index ->
                    data[index].contentId.orEmpty()
                }
            ) { index ->
                val item = data[index]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GlideImage(
                        model = item.image,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        )
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = item.title,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                }
            }
        }
    }

    if (favorList.isLoading()) {
        LoadingWidget()
    }

}

@Preview
@Composable
fun FavoriteScreenPreview() {

}