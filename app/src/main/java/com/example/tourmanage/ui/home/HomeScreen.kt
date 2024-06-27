package com.example.tourmanage.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.common.AreaDrawerContent
import com.example.tourmanage.ui.common.AreaIconWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.MainHomeViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainHomeViewModel = hiltViewModel(),
    bottomSheenOpenYn: Boolean = false,
    onDismissMenu: () -> Unit
) {
//    var bottomSheenOpenYn by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


//    LaunchedEffect(key1 = viewModel.childAreaCodesState) {
//        viewModel.childAreaCodesState.collect {
//            Timber.i("TEST_LOG | $it")
//        }
//    }

    val child = viewModel.childAreaCodesState.collectAsStateWithLifecycle()
    Timber.i("TEST_LOG | child: $child")

    if (child.isSuccess()) {
        val name = child.value.data!!
        name.get(0).name
        Text(text = name.get(0).name!!)
    }
    if (bottomSheenOpenYn) {
        ModalBottomSheet(
            modifier = Modifier.height(600.dp),
            sheetState = sheetState,
            scrimColor = Color.Black.copy(alpha = .7f),
            windowInsets = WindowInsets(0, 0, 0, 0),
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion { onDismissMenu() }
            },
            dragHandle = {
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "지역선택",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = spoqaHanSansNeoFont,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {

                    }
//                    Row() {
//                        if (curParentItem != null) {
//                            AreaIconWidget(
//                                modifier = Modifier
//                                    .width(60.dp)
//                                    .wrapContentHeight(),
//                                curParentItem, false
//                            )
//                        }
//                        Spacer(modifier = Modifier.width(10.dp))
//                        if (curChildItem != null) {
//                            AreaIconWidget(
//                                modifier = Modifier
//                                    .width(60.dp)
//                                    .wrapContentHeight(),
//                                curChildItem, true
//                            )
//                        }
//                    }
                }
            }
        ) {
            AreaDrawerContent(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
                currentParentArea = null,
                currentChildArea = null,
                detailAreaList = null,
                onClick = { areaItem, isChild ->
                    viewModel.cacheArea(areaItem, isChild)
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

}