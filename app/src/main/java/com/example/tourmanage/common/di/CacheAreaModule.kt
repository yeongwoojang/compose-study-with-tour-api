package com.example.tourmanage.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.DataStoreRepository
import com.example.tourmanage.usecase.data.area.CacheAreaUseCaseImpl
import com.example.tourmanage.usecase.data.area.GetCacheAreaUseCaseImpl
import com.example.tourmanage.usecase.data.area.RemoveCacheAreaUseCaseImpl
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.GetCacheAreaUseCase
import com.example.tourmanage.usecase.domain.area.RemoveCacheAreaUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.example.tourmanage.datastore"
)

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheAreaModule {
    @Binds
    abstract fun bindCacheAreaUseCase(uc: CacheAreaUseCaseImpl): CacheAreaUseCase

    @Binds
    abstract fun bindGetCacheAreaUseCase(uc: GetCacheAreaUseCaseImpl): GetCacheAreaUseCase

    @Binds
    abstract fun bindRemoveCacheAreaUseCase(uc: RemoveCacheAreaUseCaseImpl): RemoveCacheAreaUseCase
}