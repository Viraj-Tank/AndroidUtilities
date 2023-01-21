package com.novuspax.androidutilities.ui.home

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.work.*
import com.google.android.gms.location.*
import com.novuspax.androidutilities.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.novuspax.androidutilities.databinding.ActivityLocationMainBinding
import com.novuspax.androidutilities.utils.location.LocationWorker
import com.novuspax.androidutilities.utils.location.LocationWorkerWorkManager
import java.util.concurrent.TimeUnit


class LocationMainActivity : AppCompatActivity() {

    private val binding: ActivityLocationMainBinding by lazy {
        ActivityLocationMainBinding.inflate(layoutInflater);
    }
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var alert: AlertDialog.Builder? = null
    private val locationRequest = LocationRequest().apply {
        interval = 5000
        fastestInterval = 5000
//        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                binding.tvLatitude.text = "Latitude: " + location.latitude.toString()
                binding.tvLongitude.text = "Longitude: " + location.longitude.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createAlertDialog()
        binding.btnGetLocation.setOnClickListener {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                alert?.show()
            } else {
                startLocationUpdates()
            }
        }
        binding.btnStopLocation.setOnClickListener {
            binding.tvLatitude.text = "Latitude"
            binding.tvLongitude.text = "Longitude"
            stopLocationUpdates()
        }
        binding.btnBackgroundLocation.setOnClickListener {

            val workRequest = OneTimeWorkRequestBuilder<LocationWorkerWorkManager>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .setRequiresDeviceIdle(false)
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

            WorkManager.getInstance(this).enqueue(workRequest)


            /*val work = PeriodicWorkRequestBuilder<LocationWorker>(5, TimeUnit.SECONDS)
            .build()
            WorkManager.getInstance(this).enqueue(work)*/
        }
    }

    private fun createAlertDialog() {
        alert = AlertDialog.Builder(this).apply {
            title  = "GPS is turned off"
            setMessage("To use this feature, please turn on GPS")
            setPositiveButton("Turn on") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

}