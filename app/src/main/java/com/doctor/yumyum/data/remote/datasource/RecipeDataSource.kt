package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.api.RecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RecipeDataSource {
    suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse>

    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
    suspend fun postLike(recipeId: Int): Response<ResponseBody>
    suspend fun deleteLike(recipeId: Int): Response<ResponseBody>
    suspend fun postBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun searchRecipeList(
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
    ): Response<SearchRecipeResponse>
}

class RecipeDataSourceImp : RecipeDataSource {
    override suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse> =
        RetrofitClient.getClient().create(RankRecipeService::class.java)
            .getRecipeRank(categoryName, top, rankDatePeriod)

    override suspend fun getRecipeDetail(
        recipeId: Int
    ): Response<RecipeDetailResponse> =
        RetrofitClient.getClient().create(RecipeService::class.java).getRecipeDetail(recipeId)

    override suspend fun postLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postLike(recipeId)

    override suspend fun deleteLike(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteLike(recipeId)

    override suspend fun postBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).postBookmark(recipeId)

    override suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody> =
        RetrofitClient.getClient().create(RecipeService::class.java).deleteBookmark(recipeId)

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
        RetrofitClient.getClient().create(RecipeService::class.java)
            .searchRecipeList(
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

