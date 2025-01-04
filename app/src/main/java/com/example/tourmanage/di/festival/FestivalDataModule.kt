package com.example.tourmanage.di.festival

import com.example.tourmanage.domain.common.GetLocationBasedUseCase
import com.example.tourmanage.domain.festival.FestivalRepository
import com.example.tourmanage.domain.festival.GetFestivalUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FestivalDataModule {

    @Provides
    fun getFestivalUseCase(repository: FestivalRepository): GetFestivalUseCase {
        return GetFestivalUseCase(repository)
    }
}