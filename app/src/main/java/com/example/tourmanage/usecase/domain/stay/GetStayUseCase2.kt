package com.example.tourmanage.usecase.domain.stay

import com.example.tourmanage.data.home.PosterItem

interface GetStayUseCase2 {
    suspend operator fun invoke(areaCode: String?, sigunguCode: String?): Result<List<PosterItem>>
}