package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.FavoriteRecipeResponse
import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import com.doctor.yumyum.data.remote.response.RecipeRecommendationResponse
import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RecipeService {
    @GET("/api/v1/foods/{recipeId}")
    suspend fun getRecipeDetail(
        @Path("recipeId") recipeId: Int
    ): Response<RecipeDetailResponse>

    @POST("/api/v1/foods/{recipeId}/likes")
    suspend fun postLike(
        @Path("recipeId") recipeId: Int
    ): Response<ResponseBody>

    @DELETE("/api/v1/foods/{recipeId}/likes")
    suspend fun deleteLike(
        @Path("recipeId") recipeId: Int
    ): Response<ResponseBody>

    @POST("/api/v1/foods/{recipeId}/bookmark")
    suspend fun postBookmark(
        @Path("recipeId") recipeId: Int
    ): Response<ResponseBody>

    @DELETE("/api/v1/foods/{recipeId}/bookmark")
    suspend fun deleteBookmark(
        @Path("recipeId") recipeId: Int
    ): Response<ResponseBody>

    @GET("/api/v1/foods")
    suspend fun searchRecipeList(
        @Query("categoryName") categoryName: String,
        @Query("flavors") flavors: ArrayList<String>,
        @Query("tags") tags: ArrayList<String>,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("firstSearchTime") firstSearchTime: String,
        @Query("offset") offSet: Int,
        @Query("pageSize") pageSize: Int
    ): Response<SearchRecipeResponse>

    @GET("/api/v1/foods/favorite")
    suspend fun getFavoriteList(
        @Query("categoryName") categoryName: String
    ): Response<FavoriteRecipeResponse>
    @GET("/api/v1/foods/recommendation")
    suspend fun getRecommendation(
        @Query("categoryName") categoryName: String,
        @Query("top") top: Int,
        @Query("rankDatePeriod") rankDatePeriod: Int
    ): Response<RecipeRecommendationResponse>
}