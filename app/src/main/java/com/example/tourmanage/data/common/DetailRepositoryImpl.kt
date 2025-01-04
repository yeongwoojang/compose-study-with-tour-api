package com.example.tourmanage.data.common

import com.example.tourmanage.common.data.server.item.DetailCommonItem
import com.example.tourmanage.common.data.server.item.DetailImageItem
import com.example.tourmanage.common.data.server.item.DetailIntroItem
import com.example.tourmanage.common.data.server.item.DetailItem
import com.example.tourmanage.common.data.server.item.LocationBasedItem
import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.common.DetailRepository
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toDetailCommonItem
import com.example.tourmanage.toDetailImageList
import com.example.tourmanage.toDetailInfoList
import com.example.tourmanage.toDetailItems
import com.example.tourmanage.toLocationBasedList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): DetailRepository{
    override suspend fun getDetailInfo(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<ArrayList<DetailItem>> = runCatching {
        val response = serviceAPI.requestDetailInfo(contentId = contentId, contentTypeId = contentTypeId.value)
        val detailItems = response.toDetailItems()
        detailItems
    }.onFailure {
        throw TourMangeException.DetailInfoNullException("정보 없음.")
    }

    override suspend fun getDetailCommon(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<DetailCommonItem?> = runCatching<DetailCommonItem?> {
        val response = serviceAPI.requestDetailCommonInfo(contentId = contentId, contentTypeId = contentTypeId.value)
        val detailCommon = response.toDetailCommonItem()
        detailCommon

    }.onFailure {
        throw TourMangeException.DetailCommonInfoNullException(it.message.orEmpty())
    }

    override suspend fun getDetailIntro(
        contentId: String,
        contentTypeId: Config.CONTENT_TYPE_ID
    ): Result<List<DetailIntroItem>> = runCatching {
        val response = serviceAPI.requestDetailIntro(
            contentId = contentId,
            contentTypeId = contentTypeId.value
        )

        val introItems = response.toDetailInfoList()
        introItems.ifEmpty {
            throw TourMangeException.DetailIntroNullException("인트로 정보 조회를 실패했습니다.")
        }

    }.onFailure {
        throw TourMangeException.DetailIntroNullException(it.message.orEmpty())
    }

    override suspend fun getDetailImage(contentId: String): Result<ArrayList<DetailImageItem>> = runCatching<ArrayList<DetailImageItem>> {
        val response = serviceAPI.requestDetailImage(contentId = contentId)
        val detailImages = response.toDetailImageList()

        detailImages
    }

    override suspend fun getLocationBased(
        contentTypeId: Config.CONTENT_TYPE_ID,
        mapX: String,
        mapY: String,
        radius: String?
    ): Result<Flow<ArrayList<LocationBasedItem>>> = runCatching{
        flow {
            val response = serviceAPI.requestLocationBasedList(
                contentTypeId = contentTypeId.value,
                mapX = mapX,
                mapY = mapY
            )
            val locationBasedList = response.toLocationBasedList()
            if (locationBasedList.isEmpty()) {
                throw TourMangeException.LocationBasedNullException("위치기반 데이터 조회 실패")
            } else {
                emit(locationBasedList)
            }
        }
    }.onFailure {
        throw TourMangeException.LocationBasedNullException(it.message.orEmpty())
    }
}