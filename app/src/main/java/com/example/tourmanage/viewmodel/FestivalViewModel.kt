package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tourmanage.model.ServerDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FestivalViewModel @Inject constructor(
    private val serverRepo: ServerDataRepository
): ViewModel() {

    var a = "d"
}