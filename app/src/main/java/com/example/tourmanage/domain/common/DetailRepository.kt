package com.example.tourmanage.domain.common

import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.value.Config
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetailInfo(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<ArrayList<DetailItem>>
    suspend fun getDetailCommon(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<DetailCommonItem?>
    suspend fun getDetailIntro(contentId: String, contentTypeId: Config.CONTENT_TYPE_ID): Result<List<DetailIntroItem>>
    suspend fun getDetailImage(contentId: String): Result<ArrayList<DetailImageItem>>
    suspend fun getLocationBased(contentTypeId: Config.CONTENT_TYPE_ID, mapX: String, mapY: String, radius: String? = "5000"): Result<Flow<ArrayList<LocationBasedItem>>>
}