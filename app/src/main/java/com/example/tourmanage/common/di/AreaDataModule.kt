package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.area.GetAreaUseCaseImpl
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AreaDataModule {
    @Binds
    abstract fun bindGetAreaUseCase(uc: GetAreaUseCaseImpl): GetAreaUseCase

}