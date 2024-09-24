package com.example.tourmanage.usecase.data.festival

import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toPosterItem
import com.example.tourmanage.usecase.domain.festival.GetFestivalUseCase2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetFestivalUseCaseImpl2 @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetFestivalUseCase2 {
    override suspend fun invoke(areaCode: String, startDate: String): Result<List<PosterItem>> = runCatching{
        var date = startDate
        if (date.isEmpty()) {
            val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
            date = format.format(Date().time)
        }
        val response = serviceAPI.requestFestivalInfo(areaCode = areaCode, eventStartDate = date)

        response.toPosterItem().ifEmpty {
            throw TourMangeException.GetFestivalException("축제 정보를 불러올 수 없습니다.")
        }
    }.onFailure {
        throw TourMangeException.GetFestivalException(it.message.orEmpty())
    }
}