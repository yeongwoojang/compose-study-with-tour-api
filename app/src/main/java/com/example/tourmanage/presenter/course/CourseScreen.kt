package com.example.tourmanage.presenter.course

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.tourmanage.viewmodel.CourseViewModel

@Composable
fun CourseScreen(
    modifier: Modifier,
//    viewModel: CourseViewModel = hiltViewModel()
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("코스")
    }
}