package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import com.example.tourmanage.usecase.domain.common.GetDetailIntroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CourseViewModel constructor(
    private val getDetailInfoUseCase: GetDetailInfoUseCase,
    private val getDetailCommonUseCase: GetDetailCommonUseCase,
    private val getDetailIntroUseCase: GetDetailIntroUseCase,
    private val getDetailImageUseCase: GetDetailImageUseCase,
    ): ViewModel() {


}