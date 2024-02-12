package com.example.tourmanage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tourmanage.data.DataProvider
import com.example.tourmanage.data.Puppy
import com.example.tourmanage.ui.ui.theme.TourManageTheme

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourManageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecyclerViewContent()
                }
            }
        }
    }
}

@Composable
fun RecyclerViewContent() {
    val puppies = remember { DataProvider.puppyList }
    LazyColumn(contentPadding = PaddingValues(16.dp, 8.dp)) {
        items(
            items = puppies,
            itemContent = { PuppyListItem(it) }
        )
    }
}

@Composable
fun PuppyListItem(puppy: Puppy) {
    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(border = BorderStroke(width = 1.dp, color = Color.Black), shape = RoundedCornerShape(12.5.dp))) {
        PuppyImage(puppy = puppy)
        Column(modifier = Modifier
            .align(Alignment.CenterVertically)) {
            Text(text = puppy.title)
            Text(text = puppy.desc, modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
fun PuppyImage(puppy: Puppy) {
    Image(
        painter = painterResource(id = puppy.res),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(CornerSize(16.dp)))
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TourManageTheme {
        RecyclerViewContent()
    }
}