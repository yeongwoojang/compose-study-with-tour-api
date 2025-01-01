package com.example.tourmanage.data.festival

import com.example.tourmanage.common.repository.ServiceAPI
import com.example.tourmanage.data.home.PosterItem
import com.example.tourmanage.domain.festival.GetFestivalUseCase
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.toPosterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetFestivalUseCaseImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
): GetFestivalUseCase {
    override suspend fun invoke(areaCode: String, startDate: String): Result<Flow<List<PosterItem>>> = runCatching{
        flow {
            var date = startDate
            if (date.isEmpty()) {
                val format = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
                date = format.format(Date().time)
            }
            val response = serviceAPI.requestFestivalInfo(areaCode = areaCode, eventStartDate = date)
            val festivals = response.toPosterItem()
            if (festivals.isEmpty()) {
                throw TourMangeException.GetFestivalException("축제 정보를 불러올 수 없습니다.")
            } else {
                emit(festivals)
            }
        }
    }.onFailure {
        throw TourMangeException.GetFestivalException(it.message.orEmpty())
    }
}