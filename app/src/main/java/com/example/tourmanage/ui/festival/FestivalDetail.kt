package com.example.tourmanage.ui.festival

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.ui.common.Header
import com.example.tourmanage.viewmodel.FestivalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FestivalDetail(viewModel: FestivalViewModel, navController: NavHostController, navBackStackEntry: NavBackStackEntry) {

    Scaffold(
        topBar = {
            Header(menuName = "Festival Detail", Config.HEADER_BUTTON_TYPE.CLOSE) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp,
                    top = it.calculateTopPadding() + 20.dp,
                    bottom = it.calculateBottomPadding())
        ) {
            val t = navBackStackEntry.arguments?.getString("item")
            Text(text = "$t")
        }
    }

}