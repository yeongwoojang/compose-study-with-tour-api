package com.example.tourmanage.di.favor

import com.example.tourmanage.data.favor.AddFavorUseCaseImpl
import com.example.tourmanage.data.favor.DelFavorUseCaseImpl
import com.example.tourmanage.data.favor.GetAllFavorUseCaseImpl
import com.example.tourmanage.data.favor.GetFavorUseCaseImpl
import com.example.tourmanage.domain.favor.AddFavorUseCase
import com.example.tourmanage.domain.favor.DelFavorUseCase
import com.example.tourmanage.domain.favor.GetAllFavorUseCase
import com.example.tourmanage.domain.favor.GetFavorUseCase
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