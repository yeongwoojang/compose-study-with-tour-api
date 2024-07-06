package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.common.GetDetailCommonUseCase
import com.example.tourmanage.usecase.data.common.GetDetailImageUseCase
import com.example.tourmanage.usecase.data.common.GetDetailInfoUseCase
import com.example.tourmanage.usecase.domain.common.GetDetailCommonUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetDetailImageUseCaseImpl
import com.example.tourmanage.usecase.domain.common.GetDetailInfoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonDataModule {
    @Binds
    abstract fun bindGetDetailInfoUseCase(uc: GetDetailInfoUseCaseImpl): GetDetailInfoUseCase
    @Binds
    abstract fun bindGetDetailCommonInfoUseCase(uc: GetDetailCommonUseCaseImpl): GetDetailCommonUseCase
    @Binds
    abstract fun bindGetDetailImageInfoUseCase(uc: GetDetailImageUseCaseImpl): GetDetailImageUseCase

}