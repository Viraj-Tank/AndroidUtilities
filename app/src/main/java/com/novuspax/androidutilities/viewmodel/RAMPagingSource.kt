package com.novuspax.androidutilities.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novuspax.androidutilities.network.data.RAMResponse
import com.novuspax.androidutilities.network.retrofit.MainRepository
import java.lang.Exception

class RAMPagingSource(
    private val context: Context,
    private val repository: MainRepository
) : PagingSource<Int, RAMResponse.Result>() {
    override fun getRefreshKey(state: PagingState<Int, RAMResponse.Result>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RAMResponse.Result> {
        val currentPageKey = params.key ?: 1
        return try {
            val response = repository.getRickAndMortyResponse(currentPageKey.toString()).body()
            val previousKey = if (currentPageKey == 1) null else currentPageKey.minus(1)
            val nextKey =
                if (response?.results?.isNotEmpty() == true) currentPageKey.plus(1) else null

            LoadResult.Page(
                data = response?.results.orEmpty(),
                nextKey = nextKey,
                prevKey = previousKey
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
            LoadResult.Error(e)
        }
    }
}