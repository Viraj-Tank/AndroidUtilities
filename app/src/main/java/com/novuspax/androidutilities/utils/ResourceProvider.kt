package com.novuspax.androidutilities.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun myContext(): Context {
        return context
    }
}

data class Resource<out T>(val status: Status, val data: T?, val errorText: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null, null)
        fun <T> failed(errorText: String?): Resource<T> = Resource(Status.ERROR, null, errorText)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}