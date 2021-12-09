package com.doctor.yumyum.data.remote

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.remote.api.AuthCreationService
import com.doctor.yumyum.data.remote.api.RankRecipeService
import com.doctor.yumyum.data.remote.response.RankRecipeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun postAuthCreation(signUpModel: signUpModel): Response<ResponseBody>
    suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse>
}

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun postAuthCreation(signUpModel: signUpModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(AuthCreationService::class.java)
            .signUp(signUpModel)
    }

    override suspend fun getRecipeRank(
        categoryName: String,
        top: Int,
        rankDatePeriod: Int
    ): Response<RankRecipeResponse> =
        RetrofitClient.getClient().create(RankRecipeService::class.java)
            .getRecipeRank(categoryName, top, rankDatePeriod)

}