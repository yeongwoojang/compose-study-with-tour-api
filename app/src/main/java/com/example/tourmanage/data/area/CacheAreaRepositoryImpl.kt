package com.example.tourmanage.data.area

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.domain.area.CacheAreaRepository
import com.example.tourmanage.error.area.TourMangeException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheAreaRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): CacheAreaRepository {
    override suspend fun cacheArea(areaItem: AreaItem?, isSigungu: Boolean): Result<Boolean> = runCatching {
        requireNotNull(areaItem) { throw TourMangeException.AreaNullException("지역 데이터가 없습니다.") }
        dataStore.edit { store ->
            val key = if (isSigungu) Config.CHILD_AREA else Config.PARENT_AREA
            store[key] = Gson().toJson(areaItem)
        }
        true
    }

    override suspend fun getCacheArea(isSub: Boolean): Result<Flow<AreaItem?>> = runCatching{
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

    override suspend fun removeCacheArea(isSub: Boolean): Result<Boolean> = runCatching{
        dataStore.edit { preferences ->
            val key = if (isSub) Config.CHILD_AREA else Config.PARENT_AREA
            preferences.remove(key)
        }
        true
    }
}