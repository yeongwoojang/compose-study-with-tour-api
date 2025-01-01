package com.example.tourmanage.di.common

import com.example.tourmanage.domain.common.AreaBasedRepository
import com.example.tourmanage.domain.common.GetAreaBasedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AreaBasedDataModule {
    @Provides
    @ViewModelScoped
    fun provideAreaBasedUseCase(
        repository: AreaBasedRepository
    ): GetAreaBasedUseCase {
        return GetAreaBasedUseCase(repository)
    }
}