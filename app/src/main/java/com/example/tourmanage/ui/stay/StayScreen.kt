package com.example.tourmanage.ui.stay

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.R
import com.example.tourmanage.common.di.ViewModelFactoryProvider
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.getOptionString
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.StayViewModel
import dagger.hilt.android.EntryPointAccessors

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayScreen(modifier: Modifier, contentId: String?, close: () -> Unit) {

    val context = LocalContext.current
    val factory = EntryPointAccessors.fromActivity(
        context as Activity,
        ViewModelFactoryProvider::class.java
    ).StayViewModelFactory()

    val viewModel: StayViewModel = viewModel(
        factory = StayViewModel.provideStayViewModelFactory(factory, contentId ?: "")
    )

    LaunchedEffect(Unit) {
        viewModel.exceptionFlow.collect {
            Toast.makeText(context, "일시적인 오류가 발생했습니다.\n 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            close()
        }
    }

    val scrollState = rememberLazyListState()
    val stayDataFlow = viewModel.stayDataFlow.collectAsStateWithLifecycle()
    if (stayDataFlow.isSuccess()) {
        val stayData = stayDataFlow.value.data!!
        val images = stayData.images
        val common = stayData.common
        val info = stayData.info
        LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = if (images.isEmpty()) "" else images[0].originImgUrl ?: "",
                        contentScale = ContentScale.FillBounds,
                        contentDescription = ""
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = common?.title.orEmpty()
                    )
                    Text(
                        text = common?.addr1.orEmpty(),
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                }
                HorizontalDivider(thickness = 5.dp,)
            }

            items(
                count = info.size,
                key = { index ->
                    info.get(index).roomTitle.orEmpty()
                }
            ) { index ->
                val infoData = info[index]
                Column() {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        model = infoData.roomImg1.orEmpty(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "",
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = infoData.roomTitle.orEmpty())
                    Text(
                        text = "기준 ${infoData.roomBaseCount.isEmptyString("0")}인 (최대 ${infoData.roomMaxCount}인)",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 10.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = infoData.getOptionString(),
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "비수기: ${infoData.offWeekDayFee}원",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .padding(horizontal = 16.dp)
                    )
                    Text(
                        text = "성수기: ${infoData.peakWeekDayFee}원",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier
                            .padding(top = 3.dp, bottom = 10.dp)
                            .padding(horizontal = 16.dp)
                        )


                    HorizontalDivider()
                }


            }
        }
    } else {
        LoadingWidget()
    }


}