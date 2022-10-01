package com.novuspax.androidutilities.viewmodel


import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.novuspax.androidutilities.network.data.RAMResponse
import com.novuspax.androidutilities.network.retrofit.DispatcherProvider
import com.novuspax.androidutilities.network.retrofit.MainRepository
import com.novuspax.androidutilities.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

//    val RAMList = Pager(PagingConfig(pageSize = 20)) {
//        RAMPagingSource(
//            context = resourceProvider.myContext(),
//            repository = mainRepository
//        )
//    }.flow


    val RAMList = Pager(PagingConfig(pageSize = 20)) {
        object : PagingSource<Int, RAMResponse.Result>(){
            override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, RAMResponse.Result> {
                val currentPageKey = params.key ?: 1
                return try {
                    val response =
                        mainRepository.getRickAndMortyResponse(currentPageKey.toString()).body()
                    val previousKey = if (currentPageKey == 1) null else currentPageKey.minus(1)
                    val nextKey =
                        if (response?.results?.isNotEmpty() == true) currentPageKey.plus(1) else null

                    PagingSource.LoadResult.Page(
                        data = response?.results.orEmpty(),
                        nextKey = nextKey,
                        prevKey = previousKey
                    )
                } catch (e: Exception) {
                    Toast.makeText(resourceProvider.myContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                    PagingSource.LoadResult.Error(e)
                }
            }
            override fun getRefreshKey(state: PagingState<Int, RAMResponse.Result>): Int? = null
        }
    }.flow
}