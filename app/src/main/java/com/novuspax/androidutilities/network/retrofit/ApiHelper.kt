package com.novuspax.androidutilities.network.retrofit

import com.novuspax.androidutilities.network.data.RAMResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getRickAndMortyResponse(
        page: String
    ): Response<RAMResponse>
}