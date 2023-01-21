package com.novuspax.androidutilities.utils.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.*

class LocationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        val locationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 5000
//        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    Log.e("TAG", "onLocationResult: ${location.longitude}--${location.latitude}", )
                }
            }
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        /*fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? -> }*/
        return Result.success()
    }
}
