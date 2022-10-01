package com.novuspax.androidutilities.network.retrofit

import com.novuspax.androidutilities.network.data.RAMResponse
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getRickAndMortyResponse(
        page: String
    ): Response<RAMResponse> {
        return apiHelper.getRickAndMortyResponse(page)
    }
}