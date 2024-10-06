package com.example.tourmanage.ui.stay

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tourmanage.common.di.ViewModelFactoryProvider
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.StayViewModel
import dagger.hilt.android.EntryPointAccessors

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StayScreen(modifier: Modifier, contentId: String?) {

    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).StayViewModelFactory()

    val viewModel: StayViewModel = viewModel(
        factory = StayViewModel.provideStayViewModelFactory(factory, contentId ?: "")
    )

    val scrollState = rememberLazyListState()
    val imageFlow = viewModel.stayDataFlow.collectAsStateWithLifecycle()
    if (imageFlow.isSuccess()) {
        LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
            item {
                val imageItem = imageFlow.value.data!!
                Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        model = imageItem.get(0).originImgUrl ?: "",
                        contentDescription = ""
                    )
                }
            }

            items(50) { index ->
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    Text("$index")
                }
            }
        }
    } else {
        LoadingWidget()
    }


}