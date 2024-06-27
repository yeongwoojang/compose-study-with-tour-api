package com.example.tourmanage.usecase.data.area

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.error.area.AreaNullException
import com.example.tourmanage.usecase.domain.area.CacheAreaUseCase
import com.google.gson.Gson
import javax.inject.Inject

class CacheAreaUseCaseImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>

): CacheAreaUseCase {

    private companion object {
        val PARENT_AREA = stringPreferencesKey(
            name = "PARENT_AREA"
        )

        val CHILD_AREA = stringPreferencesKey(
            name = "CHILD_AREA"
        )
    }
    override suspend fun invoke(areaItem: AreaItem?, isChild: Boolean): Result<Boolean> = runCatching{
        requireNotNull(areaItem) { throw AreaNullException("지역 데이터가 없습니다.") }
        dataStore.edit { store ->
            val key = if (isChild) CHILD_AREA else PARENT_AREA
            store[key] = Gson().toJson(areaItem)
        }
        true
    }
}