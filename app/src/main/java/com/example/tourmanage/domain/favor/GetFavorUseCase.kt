package com.example.tourmanage.domain.favor

import com.example.tourmanage.common.data.room.FavorEntity
import com.example.tourmanage.common.value.Config
import javax.inject.Inject

class GetFavorUseCase @Inject constructor(
    private val repository: FavorRepository
){
    suspend operator fun invoke(contentTypeId: Config.CONTENT_TYPE_ID): Result<List<FavorEntity>> {
        return repository.getFavorByContentTypeId(contentTypeId)
    }
}