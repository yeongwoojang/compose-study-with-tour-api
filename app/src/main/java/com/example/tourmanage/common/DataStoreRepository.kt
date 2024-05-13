package com.example.tourmanage.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tourmanage.UiState
import com.example.tourmanage.common.data.server.item.AreaItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DataStoreRepository {
    suspend fun cacheArea(
        isChild: Boolean = false,
        areaItem: AreaItem
    ): Flow<UiState<AreaItem?>>

    fun getCachedArea(isChild: Boolean = false): Flow<UiState<AreaItem?>>

    suspend fun removeCacheArea(isChild: Boolean = false)
}

class AreaDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {

    private companion object {

        val PARENT_AREA = stringPreferencesKey(
            name = "PARENT_AREA"
        )

        val CHILD_AREA = stringPreferencesKey(
            name = "CHILD_AREA"
        )
    }

    override suspend fun removeCacheArea(isChild: Boolean) {
        dataStore.edit { preferences ->
            val key = if (isChild) CHILD_AREA else PARENT_AREA
            preferences.remove(key)

        }
    }

    override suspend fun cacheArea(isChild: Boolean, areaItem: AreaItem): Flow<UiState<AreaItem?>> {
        return flow {
            Result.runCatching {
                dataStore.edit { store ->
                    val key = if (isChild) CHILD_AREA else PARENT_AREA
                    store[key] = Gson().toJson(areaItem)
                }
            }.onFailure {
                emit(UiState.Error(it.message?: ""))
            }.onSuccess {
                emit(UiState.Success(areaItem))
            }
        }
    }

    override fun getCachedArea(isChild: Boolean): Flow<UiState<AreaItem?>> {
        return flow {
            val data = dataStore.data
                .catch { exception ->
                    emit(UiState.Error(exception.message ?: ""))
                }
                .map { preferences ->
                    val key = if (isChild) CHILD_AREA else PARENT_AREA
                    val data = preferences[key]
                    if (data != null) {
                        Gson().fromJson(preferences[key], AreaItem::class.java)
                    } else {
                        null
                    }
                }
            val result = data.firstOrNull()
            emit(UiState.Success(result))
        }
    }
}