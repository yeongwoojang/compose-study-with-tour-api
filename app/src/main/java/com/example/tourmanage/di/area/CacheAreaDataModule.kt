package com.example.tourmanage.di.area

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tourmanage.domain.area.repository.CacheAreaRepository
import com.example.tourmanage.domain.area.usecase.CacheAreaUseCase
import com.example.tourmanage.domain.area.usecase.GetCacheAreaUseCase
import com.example.tourmanage.domain.area.usecase.RemoveCacheAreaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.example.tourmanage.datastore"
)

@Module
@InstallIn(ViewModelComponent::class)
class CacheAreaModule {

    @Provides
    @ViewModelScoped
    fun provideCacheAreaUseCase(
        repository: CacheAreaRepository
    ): CacheAreaUseCase {
        return CacheAreaUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCacheAreaUseCase(
        repository: CacheAreaRepository
    ): GetCacheAreaUseCase {
        return GetCacheAreaUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveCacheAreaUseCase(
        repository: CacheAreaRepository
    ): RemoveCacheAreaUseCase {
        return RemoveCacheAreaUseCase(repository)
    }
}