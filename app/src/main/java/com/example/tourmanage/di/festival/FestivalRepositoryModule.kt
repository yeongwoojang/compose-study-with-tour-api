package com.example.tourmanage.di.festival

import com.example.tourmanage.data.festival.FestivalRepositoryImpl
import com.example.tourmanage.domain.festival.FestivalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FestivalRepositoryModule {
    @Binds
    abstract fun getFestivalRepository(rp: FestivalRepositoryImpl): FestivalRepository
}