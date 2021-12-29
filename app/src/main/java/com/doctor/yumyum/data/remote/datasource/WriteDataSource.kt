package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.FoodImage
import com.doctor.yumyum.data.model.WriteRecipe
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.api.WriteRecipeService
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.create

interface WriteDataSource {
    suspend fun postRecipeText(writeRecipe: WriteRecipe): Response<ResponseBody>
    suspend fun postRecipeImage(
        recipeId: Int,
        imgList: List<MultipartBody.Part>
    ): Response<FoodImage>
}

class WriteDataSourceImpl : WriteDataSource {
    override suspend fun postRecipeText(writeRecipe: WriteRecipe): Response<ResponseBody> =
        RetrofitClient.getClient().create(WriteRecipeService::class.java)
            .postRecipeText(writeRecipe)

    override suspend fun postRecipeImage(
        recipeId: Int,
        imgList: List<MultipartBody.Part>
    ): Response<FoodImage> =
        RetrofitClient.getClient().create(WriteRecipeService::class.java)
            .postRecipeImg(recipeId = recipeId, images = imgList)

}