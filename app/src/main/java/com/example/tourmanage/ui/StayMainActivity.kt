package com.example.tourmanage.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Transition
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.internal.util.LogUtil
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.target.CustomTarget
import com.example.tourmanage.UiState
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.data.Puppy
import com.example.tourmanage.data.StayItem
import com.example.tourmanage.ui.ui.theme.TourManageTheme
import com.example.tourmanage.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StayMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        val menu = extras?.getString(Config.MAIN_MENU_KEY) ?: "MY APP"

        val viewModel by viewModels<MainViewModel>()
        viewModel.requestStayInfo()

        setContent {
            TourManageTheme {
                MainLayout(viewModel, menu)
            }
        }
    }
}

@Composable
fun Header(menuName: String) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = menuName)
        },
        navigationIcon = {
            IconButton(onClick = {
                (context as? Activity)?.finish()
            }) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Close")
            }
        }
    )
}

@Composable
fun MainLayout(viewModel: MainViewModel = hiltViewModel(), menuName: String) {
    val stayInfo = viewModel.stayInfo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(menuName)
        }
    ) {
        when (stayInfo.value) {
            is UiState.Success -> {
                val data = stayInfo.value.data
                RecyclerViewContent(data!!)
            }
            is UiState.Error -> {

            }
            else -> {}
        }
    }
}

@Composable
fun RecyclerViewContent(stayInfo: ArrayList<StayItem>) {
    LazyColumn(contentPadding = PaddingValues(16.dp, 8.dp)) {
        items(
            items = stayInfo,
            itemContent = { StayListItem(it) }
        )
    }
}


@Composable
fun StayListItem(stayItem: StayItem) {
    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(
            border = BorderStroke(width = 1.dp, color = Color.Black),
            shape = RoundedCornerShape(12.5.dp)
        )) {
        StayImage(stayItem = stayItem)
        Column(modifier = Modifier
            .align(Alignment.CenterVertically)) {
            Text(text = stayItem.title!!, style = MaterialTheme.typography.h6)
            Text(text = stayItem.addr1!!, style = MaterialTheme.typography.caption, modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
fun StayImage(stayItem: StayItem) {
//    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
//    val imageModifier: Modifier = Modifier
//        .padding(8.dp)
//        .size(84.dp)
//        .clip(RoundedCornerShape(CornerSize(16.dp)))
//
//    Glide.with(LocalContext.current)
//        .asBitmap()
//        .load(stayItem.fullImageUrl!!)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
//                Timber.i("1")
//                bitmap.value = resource
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) {
//                Timber.i("2")
//
//            }
//        })
//
//    // bitmap 이 있으면
//    bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
//        Image(
//            bitmap = fetchedBitmap,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = imageModifier
//        )
//    }
    Timber.i("image: ${stayItem.smallImageUrl}")
    Image(
        painter = rememberImagePainter(data = stayItem.smallImageUrl),
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
//        RecyclerViewContent()
    }
}