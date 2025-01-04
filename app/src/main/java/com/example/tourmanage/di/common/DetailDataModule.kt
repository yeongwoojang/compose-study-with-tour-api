package com.example.tourmanage.di.common

import com.example.tourmanage.data.common.GetDetailCommonUseCase
import com.example.tourmanage.data.common.GetDetailImageUseCase
import com.example.tourmanage.data.common.GetDetailInfoUseCase
import com.example.tourmanage.domain.common.DetailRepository
import com.example.tourmanage.domain.common.GetDetailIntroUseCase
import com.example.tourmanage.domain.common.GetLocationBasedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DetailDataModule {
    @Provides
    fun getDetailInfoUseCase(repository: DetailRepository): GetDetailInfoUseCase {
        return GetDetailInfoUseCase(repository)
    }

    @Provides
    fun getDetailCommonUseCase(repository: DetailRepository): GetDetailCommonUseCase {
        return GetDetailCommonUseCase(repository)
    }

    @Provides
    fun getDetailIntroUseCase(repository: DetailRepository): GetDetailIntroUseCase {
        return GetDetailIntroUseCase(repository)
    }

    @Provides
    fun getDetailImageUseCase(repository: DetailRepository): GetDetailImageUseCase {
        return GetDetailImageUseCase(repository)
    }

    @Provides
    fun getLocationBasedUseCase(repository: DetailRepository): GetLocationBasedUseCase {
        return GetLocationBasedUseCase(repository)
    }
}