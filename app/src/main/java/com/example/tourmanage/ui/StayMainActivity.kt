package com.example.tourmanage.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.data.server.item.StayItem
import com.example.tourmanage.common.value.Config
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
//        viewModel.requestStayInfo()

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
//    val stayInfo = viewModel.stayInfo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Header(menuName)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp)) {
            AnimatedSearchLayout()
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(1.dp))
        }
    }
}

@Composable
fun AnimatedSearchLayout() {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    AnimatedVisibility(
        visible = isSearchMode,
        modifier = Modifier
            .fillMaxWidth(),
        enter = scaleIn() + expandHorizontally(),
        exit = scaleOut() + shrinkHorizontally()
    ) {
        BasicTextField(
            modifier = Modifier
                .background(
                    Color(0xDDDDDDDD),
                    RoundedCornerShape(10.dp)
                )
                .padding(4.dp)
                .height(36.dp),
            value = searchText,
            onValueChange = {
                searchText = it
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.primary),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = MaterialTheme.typography.body2.fontSize
            ),
            decorationBox = { innerTextField ->
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        if (searchText.isEmpty()) {
                            Text(
                                text = "검색어를 입력하세요.",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha =0.3f),
                                    fontSize = MaterialTheme.typography.body2.fontSize
                                    )
                                )
                        }
                        innerTextField()
                    }
                    IconButton(
                        onClick = {
                            //TODO 검색했을 시 동작
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = LocalContentColor.current.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        )
    }
    if (!isSearchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(top = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = { isSearchMode = true}
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.Black
                )

                Text(
                    text = "지역 검색하기",
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchableTopBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit
){
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        backgroundColor = Color.White
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            visible = searchMode,
            enter = scaleIn() + expandHorizontally(),
            exit = scaleOut() + shrinkHorizontally()
        ) {
            BasicTextField(
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp),
                value = searchText,
                onValueChange = onSearchTextChanged,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = MaterialTheme.typography.body2.fontSize
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (searchText.isEmpty()) Text(
                                text = "검색어를 입력하세요.",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = MaterialTheme.typography.body2.fontSize
                                )
                            )
                            innerTextField()
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = LocalContentColor.current.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            )
        }
    }

    if(!searchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = onSearchButtonClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.Black
                )

                Text(
                    text = "검색하기",
                    color = Color.Black,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AnimatedVisibilityTestWithDefault() {
    var isVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier
                .align(Alignment.TopCenter),
            enter = slideInVertically(initialOffsetY = {
                -it
            }),
            exit = slideOutVertically(targetOffsetY = {
                -it
            })
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 50.dp)
                .height(50.dp),
            onClick = {
                isVisible = isVisible.not()
            }) {
            Text(text = "Visibility 변경하기")
        }
    }
}

@Composable
fun RecyclerViewContent(stayInfo: ArrayList<StayItem>) {
    Timber.i("stayInfo: $stayInfo")
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayImage(stayItem: StayItem) {
    GlideImage(contentScale = ContentScale.Crop,
        model = stayItem.fullImageUrl,
        contentDescription = "",
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(CornerSize(16.dp))))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TourManageTheme {
//        RecyclerViewContent()
    }
}