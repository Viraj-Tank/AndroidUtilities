package com.novuspax.androidutilities

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.novuspax.androidutilities.utils.location.LocationHelper
import com.novuspax.androidutilities.utils.service.CounterNotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "onCreate: MainOnCreateCalled", )
        val location = LocationHelper(this)
//        location.startLocationUpdates(5000)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CounterNotificationService.COUNTER_CHANNEL_ID,
            "Counter",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used to increment counter in notification"

        // we can context here from application class so need to write context.getSystemService
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}