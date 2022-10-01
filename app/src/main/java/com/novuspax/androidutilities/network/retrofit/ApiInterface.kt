package com.novuspax.androidutilities.network.retrofit

import com.novuspax.androidutilities.network.data.RAMResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/character")
    suspend fun getRickAndMortyResponse(
        @Query("page") page: String
    ): Response<RAMResponse>

}