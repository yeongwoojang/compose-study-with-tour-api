package com.example.tourmanage.di.area

import com.example.tourmanage.domain.area.repository.ServerAreaRepository
import com.example.tourmanage.domain.area.usecase.GetAreaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ServerAreaDataModule {
    @Provides
    @ViewModelScoped
    fun provideGetAreaUseCase(
        repository: ServerAreaRepository
    ): GetAreaUseCase {
        return GetAreaUseCase(repository)
    }
}