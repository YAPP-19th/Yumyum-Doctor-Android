package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.remote.datasource.RecipeDataSourceImp
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import com.doctor.yumyum.domain.repository.RecipeRepository
import okhttp3.ResponseBody
import retrofit2.Response

class RecipeRepositoryImpl : RecipeRepository {

    private val recipeDataSource: RecipeDataSourceImp
        get() = RecipeDataSourceImp()

    override suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse> =
        recipeDataSource.getRecipeDetail(recipeId)

    override suspend fun postLike(recipeId: Int): Response<ResponseBody> =
        recipeDataSource.postLike(recipeId)

    override suspend fun deleteLike(recipeId: Int): Response<ResponseBody> =
        recipeDataSource.deleteLike(recipeId)

    override suspend fun postBookmark(recipeId: Int): Response<ResponseBody> =
        recipeDataSource.postBookmark(recipeId)

    override suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody> =
        recipeDataSource.deleteBookmark(recipeId)

    override suspend fun searchRecipeList(
        categoryName: String,
        flavors: String,
        tags: String,
        minPrice: Int,
        maxPrice: Int,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): Response<SearchRecipeResponse> =
        recipeDataSource.searchRecipeList(
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
}