package com.example.tourmanage.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tourmanage.common.AreaDataStoreRepository
import com.example.tourmanage.common.value.Config
import com.example.tourmanage.model.ServerDataRepository

open class CommonViewModel(
    private val serverRepo: ServerDataRepository,
    private val dataStore: AreaDataStoreRepository,
): ViewModel() {
    open fun requestMyLocationInfo(typeId: Config.CONTENT_TYPE_ID) {
        //_ nothing by parent
    }

}