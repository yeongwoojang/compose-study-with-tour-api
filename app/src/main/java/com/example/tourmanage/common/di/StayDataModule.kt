package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.stay.GetStayUseCaseImpl
import com.example.tourmanage.usecase.domain.stay.GetStayUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StayDataModule {
    @Binds
    abstract fun bindGetStayUseCase(uc: GetStayUseCaseImpl): GetStayUseCase
}