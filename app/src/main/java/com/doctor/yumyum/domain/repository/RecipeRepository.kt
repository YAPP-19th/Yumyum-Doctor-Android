package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.remote.response.RecipeRecommendationResponse
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
    suspend fun postLike(recipeId: Int): Response<ResponseBody>
    suspend fun deleteLike(recipeId: Int): Response<ResponseBody>
    suspend fun postBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun searchRecipeList(
        categoryName: String,
        flavors: ArrayList<String>,
        tags: ArrayList<String>,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        firstSearchTime: String,
        offset: Int,
        pageSize: Int
    ): Response<SearchRecipeResponse>

    suspend fun getRecommendation(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RecipeRecommendationResponse>
}