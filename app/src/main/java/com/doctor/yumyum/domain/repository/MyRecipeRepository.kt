package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
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
}