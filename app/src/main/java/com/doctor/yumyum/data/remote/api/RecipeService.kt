package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("/api/v1/foods/{recipeId}")
    suspend fun getRecipeDetail(
        @Path("recipeId") recipeId: Int
    ): Response<RecipeDetailResponse>
}