package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.WriteRecipe
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST

interface WriteRecipeService {

    @POST("/api/v1/foods")
    suspend fun postRecipeText(
        @Body writeRecipeModel : WriteRecipe
    ) : Response<ResponseBody>

    @Multipart
    @POST("/api/v1/foods/{id}/images")
    suspend fun postRecipeImg(

    ) : Response<FoodImage>

}