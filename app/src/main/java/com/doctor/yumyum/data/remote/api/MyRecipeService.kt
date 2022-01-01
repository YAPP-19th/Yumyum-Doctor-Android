package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyRecipeService {

    @GET("/api/v1/foods/me")
    suspend fun getMyRecipeList(
        @Query("categoryName") categoryName: String,
        @Query("flavors") flavors: String,
        @Query("tags") tags: String,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("firstSearchTime") firstSearchTime: String,
        @Query("offset") offSet: Int,
        @Query("pageSize") pageSize: Int,
        @Query("mineFoodType") mineFoodType : String,
        @Query("status") status : String?
    ): Response<SearchRecipeResponse>
}