package com.example.tourmanage.di.stay

import com.example.tourmanage.data.stay.GetStayUseCaseImpl
import com.example.tourmanage.domain.stay.GetStayUseCase
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