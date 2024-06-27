package com.example.tourmanage.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataStorePreferencesModule {
    @Provides
    fun provideDataSource(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.dataStore
    }
}