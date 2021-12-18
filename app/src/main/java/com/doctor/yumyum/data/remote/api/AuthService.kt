package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.SignInModel
import com.doctor.yumyum.data.model.SignUpModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("/api/v1/auth/creation")
    suspend fun signUp(
        @Body signUpModel: SignUpModel
    ): Response<ResponseBody>

    @POST("/api/v1/auth")
    suspend fun signIn(
        @Body signInModel: SignInModel
    ): Response<ResponseBody>
}