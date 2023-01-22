package com.novuspax.androidutilities.utils.location.backgroundfetch

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import com.novuspax.androidutilities.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*
import com.novuspax.androidutilities.network.data.RAMResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LocationService : Service() {

    private val NOTIFICATION_CHANNEL_ID = "my_notification_location"
    private val TAG = "LocationService"
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationBuilder()
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        //Location using FusedProviderClient
        fusedLocationClient?.requestLocationUpdates(
            LocationRequest().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val location = locationResult.locations.getOrNull(0)
                Toast.makeText(this@LocationService, "onLocationResultFused Success: ${location?.longitude}--${location?.latitude}", Toast.LENGTH_SHORT).show()
//                makeApiCall(locationResult.locations.getOrNull(0))
            }
        }, null)



        //Location using LOCATION_SERVICE
        /*LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    mLocation?.let {
                        Log.e(TAG, "onLocationChangedLocationService: ${location?.latitude} ${location?.longitude}", )
                    }
                }
            })*/
        return START_STICKY
    }

    private fun makeApiCall(location: Location?) {
        val apiClient = ApiClient.getInstance(this@LocationService).create(ApiClient::class.java)
        apiClient.updateLocationToDatabase().enqueue(object : Callback<RAMResponse>{
            override fun onResponse(call: Call<RAMResponse>, response: Response<RAMResponse>) {
                // can use location paramter here to send it to our database
            }

            override fun onFailure(call: Call<RAMResponse>, t: Throwable) {
                // if api call fails
            }

        })
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
    }

    private fun createNotificationBuilder() {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    fun stopBackgroundFetchingService() {
        Log.e(TAG, "BACKGROUND LOCATION FETCHING STOPPED NOW", )
        stopSelf()
    }
}