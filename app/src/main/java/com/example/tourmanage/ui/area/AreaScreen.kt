package com.example.tourmanage.ui.area

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaBasedItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.AreaViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun AreaScreen(
    viewModel: AreaViewModel = hiltViewModel(),
    menuClick: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var currentMenu by remember { mutableStateOf(Config.CONTENT_TYPE_ID.FESTIVAL) }
    var currentLocation by remember {
        mutableStateOf(LatLng(
            ServerGlobal.getCurrentGPS().mapY.toDouble(),
            ServerGlobal.getCurrentGPS().mapX.toDouble()
        ))
    }

    var markers by remember {
        mutableStateOf(ArrayList<LocationBasedItem>(emptyList()))
    }

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
    val cameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(currentLocation, 13.0)
    }

    LaunchedEffect(Unit) {
        viewModel.locationFlow.collect {
            Timber.i("${it}")
            currentLocation = LatLng(it.mapy.toDouble(), it.mapx.toDouble())
            cameraPositionState.position = CameraPosition(currentLocation, 11.0)
        }
    }

    LaunchedEffect(key1 = currentLocation, key2 = currentMenu) {
        viewModel.requestPointList(currentMenu, currentLocation.longitude, currentLocation.latitude)
    }

    Box(Modifier.fillMaxSize()) {
        NaverMap(cameraPositionState = cameraPositionState) {
            markers.forEach {
                Marker(
                    width = 20.dp,
                    height = 40.dp,
                    state = MarkerState(position = LatLng(it.mapY!!.toDouble(), it.mapX!!.toDouble())),
                    captionText = "${it.title}",
                    captionColor = Color.Green
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
                    .padding(10.dp)
                ,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = DataProvider.homeMenuList.size,
                    key = { index ->
                        DataProvider.homeMenuList[index].type
                    }
                ) { index ->
                    val item = DataProvider.homeMenuList[index]

                    val menuColor =  if (currentMenu == item.type) {
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