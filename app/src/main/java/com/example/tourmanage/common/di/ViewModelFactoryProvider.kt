package com.example.tourmanage.common.di

import com.example.tourmanage.viewmodel.StayViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun StayViewModelFactory(): StayViewModel.StayViewModelFactory
}