package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.RecipeDetailResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRecipeDetail(recipeId: Int): Response<RecipeDetailResponse>
    suspend fun postLike(recipeId: Int): Response<ResponseBody>
    suspend fun deleteLike(recipeId: Int): Response<ResponseBody>
    suspend fun postBookmark(recipeId: Int): Response<ResponseBody>
    suspend fun deleteBookmark(recipeId: Int): Response<ResponseBody>
}