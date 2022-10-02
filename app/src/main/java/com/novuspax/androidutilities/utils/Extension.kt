package com.novuspax.androidutilities.utils

import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment

inline fun <T> sdkAboveR(aboveRVersion: () -> T?): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        aboveRVersion()
    } else null
}
