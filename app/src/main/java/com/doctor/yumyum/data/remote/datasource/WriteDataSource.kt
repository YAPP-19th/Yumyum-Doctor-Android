package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.WriteRecipe
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.api.WriteRecipeService
import okhttp3.ResponseBody
import retrofit2.Response

interface WriteDataSource {
    suspend fun postRecipeText( writeRecipe : WriteRecipe): Response<ResponseBody>
}

class WriteDataSourceImpl : WriteDataSource {
    override suspend fun postRecipeText(writeRecipe: WriteRecipe): Response<ResponseBody> =
        RetrofitClient.getClient().create(WriteRecipeService::class.java)
            .postRecipeText(writeRecipe)

}