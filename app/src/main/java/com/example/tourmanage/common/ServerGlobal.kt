package com.example.tourmanage.common

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.example.tourmanage.common.data.GpsData
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.extension.isEmptyString
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume

object ServerGlobal {
    private var currentGps = GpsData()
    private val mainAreaList = ArrayList<AreaItem>()

    fun setMainAreaList(list: ArrayList<AreaItem>) {
        if (mainAreaList.isEmpty()) {
            mainAreaList.addAll(list)
        }
    }

    fun getMainAreaList(): ArrayList<AreaItem> = mainAreaList

    fun setGPS(location: Location) {
        val longitude = location.longitude.toString().isEmptyString("")
        val latitude = location.latitude.toString().isEmptyString("")
        Timber.d("setGPS() | longitude: $longitude | latitude: $latitude")
        currentGps = GpsData(longitude, latitude)
    }

    fun getCurrentGPS() = currentGps

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