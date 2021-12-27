package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.api.RecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
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
}

class RemoteDataSourceImpl : RemoteDataSource {

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
}