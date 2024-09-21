package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.common.GetTourInfoUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetTourInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TourDataModule {
    @Binds
    abstract fun bindGetTourInfoUseCase(uc: GetTourInfoUseCaseImpl): GetTourInfoUseCase
}