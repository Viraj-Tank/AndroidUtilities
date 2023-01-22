package com.novuspax.androidutilities.utils.location.backgroundfetch

import android.content.Context
import com.novuspax.androidutilities.network.data.RAMResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiClient {

    @GET("updateLocationEndPoint")
    fun updateLocationToDatabase() : Call<RAMResponse>

    companion object {
        var retrofit: Retrofit? = null
        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        fun getInstance(context: Context) : Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://enterenterenternetn.com/")
                    .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}