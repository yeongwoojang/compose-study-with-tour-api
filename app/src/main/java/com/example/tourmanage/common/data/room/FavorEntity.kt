package com.example.tourmanage.common.data.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["contentId"], unique = true)]
)
data class FavorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contentTypeId: String,
    val contentId: String,
    val title: String,
    val image: String,
)