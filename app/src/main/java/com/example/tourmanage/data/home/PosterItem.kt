package com.example.tourmanage.data.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PosterItem(
    val contentId: String,
    val contentTypeId: String,
    val imgUrl: String,
    val title: String
): Parcelable
