package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}