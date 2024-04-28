package com.example.tourmanage.ui.stay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.*
import com.example.tourmanage.viewmodel.StayViewModel
import timber.log.Timber

@Composable
fun StayAreaDrawerContent(viewModel: StayViewModel = hiltViewModel(), currentAreaCode: String, onClick: (areaItem: AreaItem, requestKey: String) -> Unit) {
    val detailAreaCode = viewModel.areaInfo.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(
                    items = ServerGlobal.getAreaCodeList(),
                    key = { index, areaItem ->
                        areaItem.code.isEmptyString()
                    }
                ) {index, areaItem ->
                    AreaItemLayout(areaItem, curAreaCode = currentAreaCode, onClick = { requestKey ->
                            onClick(areaItem, requestKey)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Divider(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp))
            Spacer(modifier = Modifier.width(10.dp))

            if (detailAreaCode.isCompleteSuccess(currentAreaCode)) {
                val detailAreaList = detailAreaCode.value.data!!
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = detailAreaList,
                        itemContent = {
                            AreaItemLayout(it, isDetail = true, curAreaCode = currentAreaCode) { areaCode ->

                            }
                        }
                    )
                }
            } else {
            }
        }
    }
}

@Composable
fun AreaItemLayout(areaItem: AreaItem, isDetail: Boolean = false, curAreaCode: String, onClick: (requestKey: String) -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = if (isDetail) {
                    colorResource(id = R.color.yellow)
                } else {
                    if (curAreaCode == areaItem.code) {
                        colorResource(id = R.color.light_coral)
                    } else {
                        colorResource(id = R.color.gainsboro)
                    }
                },
                shape = RoundedCornerShape(6.dp),
            )
            .width(80.dp)
            .wrapContentHeight()
            .padding(10.dp)
            .noRippleClickable { onClick(areaItem.code!!) },
    contentAlignment = Alignment.Center
    ) {
       Text(
           text = areaItem.name.isEmptyString(),
           style = TextStyle(
               fontSize = 12.sp,
               fontFamily = spoqaHanSansNeoFont,
               fontWeight = FontWeight.Medium,
               color = Color.Black
           )
       )
    }
}