package com.novuspax.androidutilities.network.retrofit

import com.novuspax.androidutilities.network.data.RAMResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiHelper {
    override suspend fun getRickAndMortyResponse(
        page: String
    ): Response<RAMResponse> = apiInterface.getRickAndMortyResponse(page)
}