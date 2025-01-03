package com.example.tourmanage.domain.stay

import com.example.tourmanage.data.home.PosterItem

interface GetStayUseCase {
    suspend operator fun invoke(areaCode: String?, sigunguCode: String?): Result<List<PosterItem>>
}