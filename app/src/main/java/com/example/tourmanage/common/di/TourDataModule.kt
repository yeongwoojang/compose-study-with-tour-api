package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.common.GetAreaBasedUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetAreaBasedUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TourDataModule {
    @Binds
    abstract fun bindGetAreaBasedUseCase(uc: GetAreaBasedUseCaseImpl): GetAreaBasedUseCase
}