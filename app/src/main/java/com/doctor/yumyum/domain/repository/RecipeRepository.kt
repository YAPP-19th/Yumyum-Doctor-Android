package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
}