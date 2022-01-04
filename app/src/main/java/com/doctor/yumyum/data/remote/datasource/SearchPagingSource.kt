package com.doctor.yumyum.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.doctor.yumyum.data.model.RecipeModel
import com.doctor.yumyum.data.remote.api.RecipeService

class SearchPagingSource(
    private val service: RecipeService,
    private val categoryName: String,
    private val flavors: ArrayList<String>,
    private val tags: ArrayList<String>,
    private val minPrice: Int?,
    private val maxPrice: Int?,
    private val sort: String,
    private val order: String,
    private val firstSearchTime: String,
    private val offset: Int,
    private val pageSize: Int
) : PagingSource<Int, RecipeModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeModel> {
        return try {
            val next = params.key ?: 1
            val response = service.searchPagingList(
                categoryName,
                flavors,
                tags,
                minPrice,
                maxPrice,
                sort,
                order,
                firstSearchTime,
                offset,
                pageSize
            )

            LoadResult.Page(
                data = response.body()?.foods ?: listOf(),
                prevKey = null,
                nextKey = next + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecipeModel>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}