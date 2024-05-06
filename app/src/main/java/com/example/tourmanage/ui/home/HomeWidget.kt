package com.example.tourmanage.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tourmanage.R
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.extension.noRippleClickable
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.AreaIconWidget
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.ui.ui.theme.spoqaHanSansNeoFont
import com.example.tourmanage.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeWidget(viewModel: HomeViewModel = hiltViewModel(), curParentArea: AreaItem?, curChildArea: AreaItem?, onClick: () -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.requestFestivalInfo(arrangeType = Config.ARRANGE_TYPE.O)
    }
    
    val festivalItems = viewModel.festivalItem.collectAsStateWithLifecycle()
    if (festivalItems.isSuccess()) {

        val festivalList = festivalItems.value.data!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            CurrentAreaWidget(curParentArea, curChildArea, onClick)
            MainImageRow(festivalList)
            HomeMenu(festivalList)
        }
    }

    if (festivalItems.isLoading()) {
        LoadingWidget()
    }
}

@Composable
fun CurrentAreaWidget(curParentArea: AreaItem?, curChildArea: AreaItem?, onClick: () -> Unit) {
    val isSelected = curParentArea != null && curChildArea != null

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = if (isSelected) Arrangement.SpaceBetween else Arrangement.End
        ) {
        if (isSelected) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AreaIconWidget(modifier = Modifier
                    .width(60.dp)
                    .wrapContentHeight(),
                    areaItem = curParentArea, isChild = false)
                AreaIconWidget(modifier = Modifier
                    .width(60.dp)
                    .wrapContentHeight(),
                    areaItem = curChildArea, isChild = true)
            }
        }
        Row(
            modifier = Modifier.noRippleClickable{
                onClick()
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "지역 설정",
                style = TextStyle(
                    fontFamily = spoqaHanSansNeoFont,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.dark_gray)
                )
            )
            Icon(
                imageVector = Icons.Filled.Menu,
                tint = colorResource(id = R.color.dark_gray),
                contentDescription = "Place"
            )
        }
    }
}