package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.WriteRecipe
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface WriteRecipeService {

    @POST("/api/v1/foods")
    suspend fun postRecipeText(
        @Body writeRecipeModel : WriteRecipe
    ) : Response<ResponseBody>

    @Multipart
    @POST("/api/v1/foods/{recipeId}/images")
    suspend fun postRecipeImg(
        @Path("recipeId") recipeId: Int,
        @Part images: List<MultipartBody.Part>
    ) : Response<FoodImage>

}