package com.example.tourmanage.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.DataStoreRepository
import com.example.tourmanage.usecase.data.area.CacheAreaUseCaseImpl
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
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
    @Singleton
    abstract fun bindCacheAreaUseCase(dataStoreRepository: CacheAreaUseCaseImpl): CacheAreaUseCase
}