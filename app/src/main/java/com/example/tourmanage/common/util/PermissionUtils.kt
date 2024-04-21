package com.example.tourmanage.common.util

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.tourmanage.common.ServerGlobal
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import timber.log.Timber

object PermissionUtils {
    private val REQUEST_LOCATION = 1

    /** 위치 권한 SDK 버전 29 이상**/
    @RequiresApi(Build.VERSION_CODES.Q)
    private val permissionsLocationUpApi29Impl = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )

    /** 위치 권한 SDK 버전 29 이하**/
    @TargetApi(Build.VERSION_CODES.P)
    private val permissionsLocationDownApi29Impl = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun requestLocation(context: Context) {
        if (Build.VERSION.SDK_INT >= 29) {
            if (ActivityCompat.checkSelfPermission(context,
                    permissionsLocationUpApi29Impl[0]
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context,
                    permissionsLocationUpApi29Impl[1]
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    permissionsLocationUpApi29Impl[2]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    permissionsLocationUpApi29Impl,
                    REQUEST_LOCATION
                )
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permissionsLocationDownApi29Impl[0]
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
                    context,
                    permissionsLocationDownApi29Impl[1]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    permissionsLocationDownApi29Impl,
                    REQUEST_LOCATION
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context) {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    ServerGlobal.setGPS(location)
                    Timber.i("좌표: ${location.latitude}, ${location.longitude}")
                }
            }
            .addOnFailureListener { fail ->
                Timber.i("GPS 정보 호출 실패")
            }
    }
}