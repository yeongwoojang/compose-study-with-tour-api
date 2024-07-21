package com.example.tourmanage.common.di

import com.example.tourmanage.usecase.data.favor.AddFavorUseCaseImpl
import com.example.tourmanage.usecase.data.favor.DelFavorUseCaseImpl
import com.example.tourmanage.usecase.data.favor.GetAllFavorUseCaseImpl
import com.example.tourmanage.usecase.data.favor.GetFavorUseCaseImpl
import com.example.tourmanage.usecase.domain.favor.AddFavorUseCase
import com.example.tourmanage.usecase.domain.favor.DelFavorUseCase
import com.example.tourmanage.usecase.domain.favor.GetAllFavorUseCase
import com.example.tourmanage.usecase.domain.favor.GetFavorUseCase
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavorDataModule {
    @Binds
    abstract fun addFavorUseCase(uc: AddFavorUseCaseImpl): AddFavorUseCase

    @Binds
    abstract fun getFavorUseCase(uc: GetFavorUseCaseImpl): GetFavorUseCase

    @Binds
    abstract fun delFavorUseCase(uc: DelFavorUseCaseImpl): DelFavorUseCase

    @Binds
    abstract fun getAllFavorUseCase(uc: GetAllFavorUseCaseImpl): GetAllFavorUseCase


}