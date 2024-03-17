package com.example.tourmanage.common.di

import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.model.ServerDataImpl
import com.example.tourmanage.model.ServerDataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServerRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindServerRepository(serverDataImpl: ServerDataImpl): ServerDataRepository
}