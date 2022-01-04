package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface MyRecipeRepository {
    suspend fun getMyRecipe(
        categoryName: String,
        flavors: String,
        tags: String,
        minPrice: Int?,
        maxPrice: Int?,
        sort: String,
        order: String,
        offset: Int,
        pageSize: Int,
        firstSearchTime : String,
        mineFoodType : String,
        status : String?
    ): Response<SearchRecipeResponse>

    suspend fun deleteFavorite(recipeId : Int) : Response<ResponseBody>
    suspend fun postFavorite(recipeId: Int,categoryName: String) : Response<ResponseBody>
}