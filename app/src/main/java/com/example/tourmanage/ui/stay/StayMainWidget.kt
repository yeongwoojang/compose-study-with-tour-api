package com.example.tourmanage.ui.stay

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourmanage.ui.common.AnimatedTextField
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.StayViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun StayMainWidget(viewModel: StayViewModel = hiltViewModel()) {
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    BottomDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerState = drawerState,
        drawerContent = {
            // 아래에서 위로 슬라이딩하는 컨텐츠를 정의합니다.
            Text(text = "asdasdasdasd")
        },
        // BottomDrawer의 내용을 정의합니다.
        content = {
            // Scaffold의 content 영역에 배치될 내용을 정의합니다.
            // 여기에는 일반적으로 메인 화면의 내용이 위치합니다.
            Scaffold(
                topBar = {
                    Header("숙소 찾기")
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                        Text(text = "여행지를 찾아보세요.",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = spoqaHanSansNeoFont,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        AnimatedTextField("지역명 검색", onClick = {
                            if (drawerState.currentValue == BottomDrawerValue.Closed) {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        })
                    }
                }
            }
        }
    )
}

@Composable
fun BottomDrawerDemo() {
    // BottomDrawer의 상태를 유지하기 위해 rememberDrawerState를 사용합니다.

}