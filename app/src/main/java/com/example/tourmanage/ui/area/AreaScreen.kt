package com.example.tourmanage.ui.area

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.festival.IconText
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.AreaViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun AreaScreen(
    viewModel: AreaViewModel = hiltViewModel(),
) {
    var text by remember { mutableStateOf("") }
    var currentMenu by remember { mutableStateOf(Config.CONTENT_TYPE_ID.FESTIVAL) }
    var currentLocation by remember {
        mutableStateOf(LatLng(
            ServerGlobal.getCurrentGPS().mapY.toDouble(),
            ServerGlobal.getCurrentGPS().mapX.toDouble()
        ))
    }

    var currentMarker by remember { mutableStateOf<LocationBasedItem?>(null) }
    var markers by remember { mutableStateOf(ArrayList<LocationBasedItem>(emptyList())) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        launch {
            viewModel.currentMenu.collect {
                currentMenu = it
            }
        }

        launch {
            viewModel.markersFlow.collect {
                markers = it
            }
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    val cameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(currentLocation, 13.0)
    }
    LaunchedEffect(Unit) {
        cameraPositionState.position = CameraPosition(currentLocation, 11.0)

        viewModel.locationFlow.collect {
            Timber.i("${it}")
            currentLocation = LatLng(it.mapy.toDouble(), it.mapx.toDouble())
            cameraPositionState.position = CameraPosition(currentLocation, 11.0)
        }
    }

    LaunchedEffect(key1 = currentLocation, key2 = currentMenu) {
        viewModel.requestPointList(currentMenu, currentLocation.longitude, currentLocation.latitude)
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxHeight(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = Color.White,
        sheetContainerColor = Color.White,
        sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetDragHandle = {

        },
        sheetContent = {
            if (currentMarker != null) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .padding(top = 20.dp)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val item = currentMarker!!
                    Text(
                        text = item.title.toString(),
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            if (!item.addr1.isNullOrEmpty()) {
                                IconText(
                                    modifier = Modifier,
                                    textModifier = Modifier.fillMaxWidth(0.7f),
                                    text = item.addr1.toString(),
                                    icon = Icons.Filled.LocationOn
                                )
                            }
                            if (!item.tel.isNullOrEmpty()) {
                                IconText(
                                    modifier = Modifier,
                                    textModifier = Modifier.fillMaxWidth(0.7f),
                                    text = item.tel.toString(),
                                    icon = Icons.Filled.Call
                                )
                            }
                        }
                        GlideImage(
                            model = item.mainImage.toString(),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }

                }
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            NaverMap(cameraPositionState = cameraPositionState) {
                markers.forEach { item ->
                    var width = 20.dp
                    var height = 40.dp
                    if (currentMarker == item) {
                        width = 30.dp
                        height = 50.dp
                    } else {
                        width = 20.dp
                        height = 40.dp
                    }
                    Marker(
                        width = width,
                        height = height,
                        state = MarkerState(
                            position = LatLng(
                                item.mapY!!.toDouble(),
                                item.mapX!!.toDouble()
                            )
                        ),
                        captionText = "${item.title}",
                        captionColor = Color.Green,
                        onClick = {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                            currentMarker = item
                            false
                        }
                    )
                }

            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        count = DataProvider.homeMenuList.size,
                        key = { index ->
                            DataProvider.homeMenuList[index].type
                        }
                    ) { index ->
                        val item = DataProvider.homeMenuList[index]

                        val menuColor = if (currentMenu == item.type) {
                            Color.Black
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                        Box(
                            modifier = Modifier
                                .background(
                                    color = menuColor,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(10.dp)
                                .clickable {
                                    viewModel.setMenu(item.type)
                                }
                        ) {
                            Text(
                                text = item.title,
                                color = Color.White,
                                style = TextStyle(
                                    fontFamily = spoqaHanSansNeoFont,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black
                        ),
                        maxLines = 1,
                        placeholder = {
                            Text(text = "검색어를 입력하세요.")
                        },
                        value = text,
                        onValueChange = {
                            text = it
                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.TopEnd),
                        onClick = {
                            viewModel.handleQuery(text)

                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = Color.Black,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }




//    ModalBottomSheet(
//        modifier = Modifier
//            .height(200.dp),
//        containerColor = Color.White,
//        sheetState = sheetState,
//        onDismissRequest = onDismissMenu,
//        shape = RoundedCornerShape(8.dp),
//        content = {
//            if (currentMarker != null) {
//                Column(modifier = Modifier
//                    .heightIn(min = 210.dp, max = 700.dp)
//                    .padding(top = 20.dp)
//                    .padding(horizontal = 16.dp),
//                    verticalArrangement = Arrangement.spacedBy(20.dp)
//                ) {
//                    val item = currentMarker!!
//                    Text(
//                        text = item.title.toString(),
//                        color = Color.Black,
//                        style = TextStyle(
//                            fontSize = 15.sp,
//                            fontFamily = spoqaHanSansNeoFont,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    Row(modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Column(
//                            verticalArrangement = Arrangement.spacedBy(10.dp)
//                        ) {
//                            IconText(
//                                modifier = Modifier,
//                                textModifier = Modifier.fillMaxWidth(0.7f),
//                                text = item.addr1.toString(),
//                                icon = Icons.Filled.LocationOn
//                            )
//                            IconText(
//                                modifier = Modifier,
//                                textModifier = Modifier.fillMaxWidth(0.7f),
//                                text = item.tel.toString(),
//                                icon = Icons.Filled.Call
//                            )
//                        }
//                        GlideImage(
//                            model = item.mainImage.toString(),
//                            contentDescription = "",
//                            contentScale = ContentScale.FillBounds,
//                            modifier = Modifier
//                                .size(80.dp)
//                                .clip(RoundedCornerShape(10.dp))
//                                .clickable {
//                                    bottomSheetHeight = 1f
//                                }
//
//                        )
//                    }
//
//                }
//            }
//
//
//        },
//        dragHandle = {
//
//        }
//    )
}