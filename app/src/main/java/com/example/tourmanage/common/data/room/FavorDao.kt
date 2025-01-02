package com.example.tourmanage.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavorEntity): Long

    @Query("SELECT * FROM FavorEntity")
    suspend fun getFavorAll(): List<FavorEntity>

    @Query("SELECT * FROM FavorEntity WHERE contentTypeId = :contentTypeId")
    suspend fun getFavorByContentTypeId(contentTypeId: String): List<FavorEntity>

    @Query("DELETE FROM FavorEntity WHERE contentId = :contentId")
    suspend fun deleteFavorItem(contentId: String)

    @Query("DELETE FROM FAVORENTITY")
    suspend fun deleteFavorAll()

    @Query("SELECT * FROM FavorEntity WHERE contentId = :contentId")
    suspend fun isFavor(contentId: String): FavorEntity?
}