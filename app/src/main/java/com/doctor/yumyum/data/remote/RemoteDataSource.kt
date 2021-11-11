package com.doctor.yumyum.data.remote

import android.telecom.Call
import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.signUpModel
import com.doctor.yumyum.data.remote.api.AuthCreationService
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun postAuthCreation(signUpModel: signUpModel): Response<ResponseBody>
}

class RemoteDataSourceImpl() : RemoteDataSource {
    override suspend fun postAuthCreation(signUpModel: signUpModel): Response<ResponseBody> {
        return RetrofitClient.getClient().create(AuthCreationService::class.java)
            .signUp(signUpModel)
    }
}