package com.example.tourmanage.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.data.server.item.TourItem
import com.example.tourmanage.common.extension.isError
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.viewmodel.LocalTourViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LocalTourActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        val menu = extras?.getString(Config.MAIN_MENU_KEY) ?: "MY APP"
        val viewModel by viewModels<LocalTourViewModel>()
        setContent {
            TourManageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainForm(menu, viewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainForm(menuName: String, viewModel: LocalTourViewModel = hiltViewModel()) {
    val areaInfos = viewModel.areaInfo.collectAsStateWithLifecycle()
    // TODO 지역 이름만 가져 와서 셋팅

    LaunchedEffect(Unit) {
        viewModel.requestAreaList()

    }

    Scaffold(
        topBar = {
            Header(menuName = menuName)
        }
    ) {
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "지역 선택", fontSize = 15.sp, modifier = Modifier.padding(start = 25.dp))
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                when{
                    areaInfos.isLoading() -> {

                    }
                    areaInfos.isSuccess() -> {
                        val areaInfo = areaInfos.value.data?: emptyList()
                        AreaListUi(list = areaInfo.toList(), viewModel)
                    }
                    areaInfos.isError() -> {

                    }

                }
                CardListView(viewModel)
            }

        }
    }
}


@Composable
fun CardListView(viewModel: LocalTourViewModel) {
    val tourInfos = viewModel.tourInfo.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.requestTourInfo()
    }

    when{
        tourInfos.isLoading() -> {

        }
        tourInfos.isSuccess() -> {
            val tourInfo = tourInfos.value.data?: emptyList()
            Timber.i("tourInfo $tourInfo")
            val cardList = remember { tourInfo }
            LazyColumn(
                contentPadding = PaddingValues(start = 25.dp, top = 20.dp, end = 20.dp, bottom = 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(
                    items = cardList,
                    itemContent = { CardListItem(it) }
                )
            }
        }
        tourInfos.isError() -> {
            Timber.i("투어 정보 에러")
        }

    }
}

@Composable
fun CardListItem(item: TourItem) {
    Card(
        modifier = Modifier
            .aspectRatio(2f),
        shape = RoundedCornerShape(CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(CornerSize(16.dp))
                )
        ) {
            TourImage(item)
            Text(
                text = item.title!!, color = Color.Gray, fontSize = 20.sp,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun AreaListUi(list: List<AreaItem>, viewModel: LocalTourViewModel) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = 25.dp, top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = list, itemContent = { SubItem(item = it.name?:""){viewModel.requestTourInfo(it.code)} })
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TourImage(tourItem: TourItem) {
    GlideImage(contentScale = ContentScale.Crop,
        model = tourItem.fullImageUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubItem(item: String, onClick:() -> Unit) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .clickable {

            },
        shape = RoundedCornerShape(CornerSize(20.dp)),
        elevation = 2.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TourManageTheme {

    }
}