package com.example.tourmanage.common.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavorEntity::class], version = 1)
abstract class FavorDatabase: RoomDatabase() {
    abstract fun favorDao(): FavorDao

}