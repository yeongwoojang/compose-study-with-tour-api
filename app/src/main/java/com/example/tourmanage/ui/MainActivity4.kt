package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourmanage.R
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.CardItem
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity4 : ComponentActivity() {
    @Inject lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        val menu = extras?.getString(Config.MAIN_MENU_KEY) ?: "MY APP"

        setContent {
            TourManageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainForm(menu)
                }
            }
        }
    }
}

@Composable
fun MainForm(menuName: String) {
    val subList = mutableListOf<String>("주제1", "주제2", "주제3", "주제4", "주제5", "주제6", "주제7", "주제8")
    Scaffold(
        topBar = {
            Header(menuName = menuName)
        }
    ) {
        LazyColumn() {
            item {
                TopCardListView()
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "소주제 영역", fontSize = 15.sp, modifier = Modifier.padding(start = 25.dp))
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                LazyHorizontalGrid(rows = GridCells.Fixed(2),
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(start = 25.dp, top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = subList, itemContent = { SubItem(item = it) })
                }
            }
        }
    }
}

@Composable
fun TopCardListView() {
    val cardLit = remember { DataProvider.cardList }
    LazyRow(contentPadding = PaddingValues(start = 25.dp, top = 20.dp, end = 20.dp, bottom = 10.dp), modifier = Modifier
        .fillMaxWidth()
        .height(250.dp),
    horizontalArrangement = Arrangement.spacedBy(15.dp)) {
        items(
            items = cardLit,
            itemContent = { CardListItem(it) }
        )
    }
}

@Composable
fun CardListItem(item: CardItem) {
    Card(modifier = Modifier
        .aspectRatio(2f),
        shape = RoundedCornerShape(CornerSize(16.dp)),
        elevation = 6.dp) {
        Box(modifier = Modifier
            .background(color = Color.Transparent, shape = RoundedCornerShape(CornerSize(16.dp)))) {
            Image(
                painter = painterResource(id = item.res),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize() // 이미지가 카드의 전체 영역을 채우도록 함
                    .clip(RoundedCornerShape(CornerSize(16.dp)))
            )
            Text(text = "컨텐츠 설명이 들어가는 곳", color = Color.Gray, fontSize = 20.sp,
                modifier = Modifier
                    .padding(20.dp))
        }
    }
}

@Composable
fun SubItem(item: String) {
    Card(modifier = Modifier
        .width(80.dp)
        .clickable {

        },
        shape = RoundedCornerShape(CornerSize(20.dp)),
        elevation = 2.dp) {
        Box(modifier = Modifier
            .background(color = Color.Yellow),
            contentAlignment = Alignment.Center) {
            Text(text = item, fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TourManageTheme {
//        MainForm()
    }
}