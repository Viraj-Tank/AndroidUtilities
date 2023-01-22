package com.novuspax.androidutilities.utils.location

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.*

class OldLocationHelper(context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                Log.e("TAG", "onLocationResult: ${location.longitude}--${location.latitude}", )
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(interval: Long) {
        val locationRequest = LocationRequest().apply {
            this.interval = interval
            fastestInterval = interval
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
