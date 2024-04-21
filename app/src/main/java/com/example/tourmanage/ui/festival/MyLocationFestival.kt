package com.example.tourmanage.ui.festival

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.viewmodel.FestivalViewModel
import timber.log.Timber
import com.example.tourmanage.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyLocationFestival(viewModel: FestivalViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.requestFestival()
    }

    val festivalItemsByArea = viewModel.festivalItem.collectAsStateWithLifecycle()

    if (festivalItemsByArea.isSuccess()) {
        val festivalItems = festivalItemsByArea.value.data!!
        Timber.i("TEST_LOG | festivalItemsByArea: ${festivalItemsByArea.value.data!!}")
        Column {
            Text(
                text = "우리 동네에서 열리는 축제",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(festivalItems) { index, item ->
                    Column(modifier = Modifier
                        .width(150.dp)
                    ) {
                        GlideImage(
                            model = item.mainImage,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.title.isEmptyString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily =  FontFamily.Monospace,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = item.addr1.isEmptyString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 8.sp,
                                fontFamily =  FontFamily.Monospace,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(id = R.color.dark_gray)
                            )
                        )
                    }
                }
            }
        }
    }
}