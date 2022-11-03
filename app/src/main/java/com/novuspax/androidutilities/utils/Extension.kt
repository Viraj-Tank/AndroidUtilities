package com.novuspax.androidutilities.utils

import android.app.DownloadManager
import android.app.Service
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment

inline fun <T> sdkAbove10(aboveAndroid10: () -> T?): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        aboveAndroid10()
    } else null
}

inline fun <T> sdkAbove12(aboveAndroid12: () -> T?): T? {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
        aboveAndroid12()
    } else null
}
