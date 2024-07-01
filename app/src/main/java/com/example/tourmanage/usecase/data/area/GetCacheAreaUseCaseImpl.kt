package com.example.tourmanage.usecase.data.area

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.error.area.TourMangeException
import com.example.tourmanage.usecase.domain.area.GetCacheAreaUseCase
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCacheAreaUseCaseImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): GetCacheAreaUseCase {
    override suspend fun invoke(isSub: Boolean): Result<Flow<AreaItem?>> = runCatching{
        flow {
            val data = dataStore.data
                .catch {
                    throw TourMangeException.AreaException("캐싱된 지역 불러오기 실패")
                }
                .map { preferences ->
                    val key = if (isSub) Config.CHILD_AREA else Config.PARENT_AREA
                    val data = preferences[key]
                    data?.let {
                        Gson().fromJson(preferences[key], AreaItem::class.java)
                    }
                }
            emit(data.firstOrNull())
        }
    }
}