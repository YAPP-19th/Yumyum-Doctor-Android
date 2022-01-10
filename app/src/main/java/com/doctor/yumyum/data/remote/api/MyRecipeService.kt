package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.SearchRecipeResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MyRecipeService {

    @GET("/api/v1/foods/me")
    suspend fun getMyRecipeList(
        @Query("categoryName") categoryName: String,
        @Query("flavors") flavors: ArrayList<String>,
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

    @DELETE("/api/v1/foods/{recipeId}/favorite")
    suspend fun deleteFavorite(
        @Path("recipeId") recipeId: Int,
    ) : Response<ResponseBody>

    @POST("/api/v1/foods/{recipeId}/favorite")
    suspend fun postFavorite(
        @Path("recipeId") recipeId: Int,
        @Body categoryName: String
    ) : Response<ResponseBody>
}