package com.example.tourmanage.usecase.data.area

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.google.gson.Gson
import javax.inject.Inject

class CacheAreaUseCaseImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): CacheAreaUseCase {

    override suspend fun invoke(areaItem: AreaItem?, isSub: Boolean): Result<Boolean> = runCatching{
        requireNotNull(areaItem) { throw TourMangeException.AreaNullException("지역 데이터가 없습니다.") }
        dataStore.edit { store ->
            val key = if (isSub) Config.CHILD_AREA else Config.PARENT_AREA
            store[key] = Gson().toJson(areaItem)
        }
        true
    }
}