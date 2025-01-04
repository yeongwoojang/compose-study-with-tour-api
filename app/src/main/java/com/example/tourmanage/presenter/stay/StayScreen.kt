package com.example.tourmanage.presenter.stay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.UiState
import com.example.tourmanage.di.ViewModelFactoryProvider
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.getOptionString
import com.example.tourmanage.presenter.components.LoadingWidget
import com.example.tourmanage.presenter.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.presenter.viewmodel.StayViewModel
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayScreen(modifier: Modifier, posterItem: PosterItem?, close: () -> Unit) {
    val context = LocalContext.current
    val factory = EntryPointAccessors.fromActivity(
        context as Activity,
        ViewModelFactoryProvider::class.java
    ).StayViewModelFactory()

    val viewModel: StayViewModel = viewModel(
        factory = StayViewModel.provideStayViewModelFactory(factory, posterItem?.contentId ?: "")
    )

    LaunchedEffect(Unit) {
        viewModel.exceptionFlow.collect {
            Toast.makeText(context, "일시적인 오류가 발생했습니다.\n 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            close()
        }
    }

    val isFavorited = remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()
    val stayDataFlow = viewModel.stayDataFlow.collectAsStateWithLifecycle()

    val toggleFavor = viewModel.toggleFavorFlow.collectAsStateWithLifecycle()

    val telNumber = remember { mutableStateOf("") }

    LaunchedEffect(toggleFavor.value) {
        when(toggleFavor.value) {
            is UiState.Success -> {
                if (!toggleFavor.value.data!!) {
                    Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    isFavorited.value = false
                }
            }
            else ->{}
        }
    }

    LaunchedEffect(Unit) {
        launch {
            viewModel.isFavor.collect {
                isFavorited.value = it
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            makePhoneCall(context, telNumber.value)
        } else {
            Toast.makeText(context, "전화걸기 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun requestCallPermission(tel: String) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            makePhoneCall(context, tel)
        } else {
            permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
        }
    }

    val isVisibleAppBar by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (stayDataFlow.isSuccess()) {
            val stayData = stayDataFlow.value.data!!
            val images = stayData.images
            val common = stayData.common
            val info = stayData.info
            telNumber.value = stayData.common?.tel.orEmpty()
            LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                        if (images.isNotEmpty() && !images[0].originImgUrl.isNullOrEmpty()) {
                            GlideImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                model = images[0].originImgUrl,
                                contentScale = ContentScale.FillBounds,
                                contentDescription = "",
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "이미지를 불러올 수 없습니다.",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = spoqaHanSansNeoFont,
                                    )
                                )
                            }
                        }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = common?.addr1.orEmpty(),
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Medium,
                                ),
                            )
                            Text(
                                text = common?.tel.orEmpty(),
                                style = TextStyle(
                                    fontSize = 9.sp,
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Medium,
                                ),
                            )
                        }
                    }
                }

                items(
                    count = info.size,
                    key = { index ->
                        info.get(index).roomTitle.orEmpty()
                    }
                ) { index ->
                    val infoData = info[index]
                    Column() {
                        if (!infoData.roomImg1.isNullOrEmpty()) {
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
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "이미지를 불러올 수 없습니다.",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = spoqaHanSansNeoFont,
                                    )
                                )
                            }
                        }
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
                                fontSize = 10.sp,
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
                                fontSize = 10.sp,
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
                                fontSize = 10.sp,
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

        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .background(color = if (isVisibleAppBar) Color.White else Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.size(50.dp),
                onClick = {
                    requestCallPermission(telNumber.value)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    tint = if (isVisibleAppBar) Color.Black else Color.White,
                    contentDescription = ""
                )
            }
            //TODO 탑바 타이틀 지정 필요
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = posterItem?.title.isEmptyString("타이틀"),
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = spoqaHanSansNeoFont,
                    )
                )
            }

            IconButton(
                modifier = Modifier.size(50.dp),
                onClick = {
                    if (posterItem != null) {
                        if (isFavorited.value) {
                            isFavorited.value = false
                            viewModel.requestDelFavor(posterItem)
                        } else {
                            isFavorited.value = true
                            viewModel.requestToggleFavor(posterItem)
                        }
                    } else {
                        Toast.makeText(context, "다시 요청해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    tint = if (isVisibleAppBar) {
                        if (isFavorited.value) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                    } else {
                        if (isFavorited.value) {
                            Color.Red
                        } else {
                            Color.White
                        }
                    },
                    contentDescription = ""
                )
            }
        }
    }
}
fun makePhoneCall(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
        == PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (phoneNumber.isNotEmpty()) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "전화번호가 공개되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "전화걸기 권한이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "전화걸기 권한이 없습니다.", Toast.LENGTH_SHORT).show()
    }
}