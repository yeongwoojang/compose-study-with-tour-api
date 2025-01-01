package com.example.tourmanage.di

import com.example.tourmanage.presenter.viewmodel.StayViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun StayViewModelFactory(): StayViewModel.StayViewModelFactory
}