package com.example.tourmanage.di.common

import com.example.tourmanage.data.common.DetailRepositoryImpl
import com.example.tourmanage.domain.common.DetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailRepositoryModule {
    @Binds
    abstract fun getDetailRepository(rp: DetailRepositoryImpl): DetailRepository
}