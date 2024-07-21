package com.example.tourmanage.common.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tourmanage.common.data.room.FavorDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RomDatabaseModule {
    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): FavorDatabase {
        return Room.databaseBuilder(
            context,
            FavorDatabase::class.java,
            "favor-database"
        ).build()
    }

    @Provides
    fun getFavorDao(database: FavorDatabase) = database.favorDao()
}