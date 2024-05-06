package com.example.tourmanage.common

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.util.Locale
import kotlin.coroutines.resume

object ServerGlobal {
    private var currentGPS = Pair<String, String>("", "")
    private val parentAreaList = ArrayList<AreaItem>()
    private var currentParentArea: AreaItem? = null
    private var currentChildArea: AreaItem? = null

    fun setAreaCodeList(areaCodeList: ArrayList<AreaItem>) {
        parentAreaList.addAll(areaCodeList)
    }

    fun getParentAreaList(): ArrayList<AreaItem> = parentAreaList

    fun getAreaCode(areaName: String) = parentAreaList.find { it.name == areaName }?.code

    fun setGPS(location: Location) {
        val longitude = location.longitude.toString().isEmptyString("")
        val latitude = location.latitude.toString().isEmptyString("")
        Timber.d("setGPS() | longitude: $longitude | latitude: $latitude")
        currentGPS = Pair(longitude, latitude)
    }

    fun getCurrentGPS() = currentGPS

    fun setCurrentParentArea(parentArea: AreaItem?) {
        currentParentArea = parentArea
    }

    fun getCurrentParentArea() = currentParentArea

    fun setCurrentChildArea(childArea: AreaItem?) {
        currentChildArea = childArea
    }

    fun getCurrentChildArea() = currentChildArea

    suspend fun getAddress(context: Context, lat: Double, lng: Double): Address? =
        suspendCancellableCoroutine<Address?> { cancellableContinuation ->
            val geocoder = Geocoder(context, Locale.KOREA)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(lat, lng, 1) { addresses ->
                        cancellableContinuation.resume(addresses.firstOrNull())
                    }
                } else {
                    val address = geocoder.getFromLocation(lat, lng, 1)?.firstOrNull()
                    cancellableContinuation.resume(address)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                cancellableContinuation.cancel()
            }
        }
}