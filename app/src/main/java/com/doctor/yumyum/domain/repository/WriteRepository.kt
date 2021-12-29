package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.WriteRecipe
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

interface WriteRepository {
    suspend fun postRecipeText( writeRecipe : WriteRecipe): Response<ResponseBody>
    suspend fun postRecipeImage(
        recipeId: Int,
        imgList: List<MultipartBody.Part>
    ): Response<FoodImage>
}