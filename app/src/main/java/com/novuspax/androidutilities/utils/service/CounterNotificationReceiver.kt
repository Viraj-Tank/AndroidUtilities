package com.novuspax.androidutilities.utils.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.novuspax.androidutilities.utils.utility.Counter

class CounterNotificationReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent?) {
        val service = CounterNotificationService(context)
        service.showNotification(++Counter.value)
    }

}