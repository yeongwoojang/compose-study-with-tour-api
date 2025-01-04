package com.example.tourmanage.presenter.favorite

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.tourmanage.presenter.components.LoadingWidget
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.presenter.viewmodel.FavorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier,
    viewModel: FavorViewModel = hiltViewModel(),
    close: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getFavorAll()

        launch {
            viewModel.noDataFlow.collect {
                if (it) {
                    Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                    close()
                }
            }
        }
    }



    val favorList = viewModel.favorDataFlow.collectAsStateWithLifecycle()

    if (favorList.isSuccess()) {
        val data = favorList.value.data!!

        LazyColumn(
            modifier = modifier
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
                    if (item.image.isNotEmpty()) {
                        GlideImage(
                            model = item.image,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                    } else {
                        Box(modifier = Modifier
                            .height(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "이미지를 불러올 수 없습니다.",
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = spoqaHanSansNeoFont,
                                )
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.width(150.dp),
                        text = item.title,
                        color = Color.Black,
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