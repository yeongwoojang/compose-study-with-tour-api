package com.example.tourmanage.di.favor

import com.example.tourmanage.data.favor.FavorRepositoryImpl
import com.example.tourmanage.domain.favor.FavorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavorRepositoryModule {

    @Binds
    abstract fun getFavorRepository(rp: FavorRepositoryImpl): FavorRepository

}