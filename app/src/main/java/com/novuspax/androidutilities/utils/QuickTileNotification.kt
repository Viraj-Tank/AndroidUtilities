package com.novuspax.androidutilities.utils

import android.content.Intent
import android.os.IBinder
import android.service.quicksettings.TileService
import com.novuspax.androidutilities.utils.service.CounterNotificationService

class QuickTileNotification: TileService() {
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onTileAdded() {
        super.onTileAdded()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
    }

    override fun onStartListening() {
        super.onStartListening()
    }

    override fun onStopListening() {
        super.onStopListening()
    }

    override fun onClick() {
        CounterNotificationService(this).showNotification(++Counter.value)
        super.onClick()
    }
}