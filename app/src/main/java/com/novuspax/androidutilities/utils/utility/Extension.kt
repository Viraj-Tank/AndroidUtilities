package com.novuspax.androidutilities.utils.utility

import android.net.Uri
import android.os.Build
import android.os.Parcelable

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

fun ArrayList<Parcelable>.getURIsFromList():ArrayList<Uri?> {
    val uriList = arrayListOf<Uri?>()
    forEach {
        uriList.add(it as? Uri)
    }
    return uriList
}
