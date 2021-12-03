package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.SignUpModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthCreationService {
    @POST("/api/v1/auth/creation")
    suspend fun signUp(
        @Body signUpModel: SignUpModel
    ): Response<ResponseBody>
}