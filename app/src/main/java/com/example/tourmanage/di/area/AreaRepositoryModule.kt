package com.example.tourmanage.di.area

import com.example.tourmanage.data.area.CacheAreaRepositoryImpl
import com.example.tourmanage.data.area.ServerAreaRepositoryImpl
import com.example.tourmanage.domain.area.repository.CacheAreaRepository
import com.example.tourmanage.domain.area.repository.ServerAreaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AreaRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun getCacheAreaRepository(rp: CacheAreaRepositoryImpl): CacheAreaRepository

    @Binds
    @ViewModelScoped
    abstract fun getServerAreaRepository(rp: ServerAreaRepositoryImpl): ServerAreaRepository
}