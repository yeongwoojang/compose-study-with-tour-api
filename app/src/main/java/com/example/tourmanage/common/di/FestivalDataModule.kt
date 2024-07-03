package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.common.GetLocationBasedUseCaseImpl
import com.example.tourmanage.usecase.data.festival.GetFestivalUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetLocationBasedUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FestivalDataModule {

    @Binds
    abstract fun bindGetFestivalInfoUseCase(uc: GetFestivalUseCaseImpl): GetFestivalUseCase

    @Binds
    abstract fun bindGetLocationBasedUseCase(uc: GetLocationBasedUseCaseImpl): GetLocationBasedUseCase
}