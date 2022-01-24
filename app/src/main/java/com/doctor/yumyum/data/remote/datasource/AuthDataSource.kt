package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.api.AuthService
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthDataSource {
    suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody>
    suspend fun signIn(signInModel: SignInModel): Response<ResponseBody>
    suspend fun refreshToken(): Response<ResponseBody>
}

class AuthDataSourceImpl : AuthDataSource {

    override suspend fun signUp(signUpModel: SignUpModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(AuthService::class.java)
            .signUp(signUpModel)

    override suspend fun signIn(signInModel: SignInModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(AuthService::class.java).signIn(signInModel)

    override suspend fun refreshToken(): Response<ResponseBody> =
        RetrofitClient.getClient().create(AuthService::class.java).refreshToken()
}