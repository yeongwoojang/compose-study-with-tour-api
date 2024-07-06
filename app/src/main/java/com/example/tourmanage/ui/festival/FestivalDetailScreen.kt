package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.tourmanage.UiState
import com.example.tourmanage.common.extension.isLoading
import com.example.tourmanage.common.extension.isSuccess
import com.example.tourmanage.ui.components.LoadingWidget
import com.example.tourmanage.viewmodel.FestivalDetail
import com.example.tourmanage.viewmodel.FestivalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalDetailScreen(
    viewModel: FestivalViewModel,
    festivalDetailState: State<UiState<FestivalDetail>>
) {
    if (festivalDetailState.isSuccess()) {
        val festivalDetail = festivalDetailState.value.data!!
        val detailInfo = festivalDetail.detailInfo
        val detailCommon = festivalDetail.detailCommon
        val detailImage = festivalDetail.detailImageItems

        Column {
            Text(text = "${detailInfo[0].contentId}")
            Text(text = "${detailCommon?.contentId}")
            Text(text = "${detailImage[0]?.contentId}")
        }

    }

    if (festivalDetailState.isLoading()) {
        LoadingWidget()
    }
}