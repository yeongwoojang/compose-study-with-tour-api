package com.example.tourmanage.di.room

import android.content.Context
import androidx.room.Room
import com.example.tourmanage.common.data.room.FavorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {
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