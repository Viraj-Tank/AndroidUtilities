package com.novuspax.androidutilities.di

import com.novuspax.androidutilities.network.retrofit.ApiHelper
import com.novuspax.androidutilities.network.retrofit.ApiHelperImpl
import com.novuspax.androidutilities.network.retrofit.ApiInterface
import com.novuspax.androidutilities.network.retrofit.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getBaseUrl(): String = "https://rickandmortyapi.com/"

//    @Provides
//    @Singleton
//    @Named("BASEURL_OTHER")
//    fun getOtherBaseUrl(): String = "https://rickandmortyapi.com/"

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        url: String,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesApiInterface(
        retrofit: Retrofit
    ): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesApiInterfaceImpl(
        apiHelper: ApiHelperImpl
    ): ApiHelper {
        return apiHelper
    }

    @Provides
    @Singleton
    fun providesCoroutine(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
    }

}