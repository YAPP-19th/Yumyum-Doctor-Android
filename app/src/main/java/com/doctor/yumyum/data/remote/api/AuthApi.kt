package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.signUpModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthCreationService {
    @POST("/api/v1/auth/creation")
    fun signUp(
        @Body signUpModel: signUpModel
    ): Call<ResponseBody>
}