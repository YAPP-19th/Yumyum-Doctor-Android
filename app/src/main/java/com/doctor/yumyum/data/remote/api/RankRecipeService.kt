package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RankRecipeService {
    @GET("/api/v1/food/{categoryType}/rank")
    suspend fun getRecipeRank(
        @Path("categoryType") categoryType: String,
        @Query("top") top: Int
    ): Response<RankRecipeResponse>
}