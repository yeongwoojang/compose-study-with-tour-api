package com.example.tourmanage.di.common

import com.example.tourmanage.data.common.AreaBasedRepositoryImpl
import com.example.tourmanage.domain.common.AreaBasedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AreaBasedRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun getAreaBasedRepository(rp: AreaBasedRepositoryImpl): AreaBasedRepository
}