package com.example.tourmanage.usecase.data.area

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.usecase.domain.area.RemoveCacheAreaUseCase
import javax.inject.Inject

class RemoveCacheAreaUseCaseImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): RemoveCacheAreaUseCase {
    override suspend fun invoke(isSub: Boolean): Result<Boolean> = runCatching{
        dataStore.edit { preferences ->
            val key = if (isSub) Config.CHILD_AREA else Config.PARENT_AREA
            preferences.remove(key)
        }
        true
    }
}