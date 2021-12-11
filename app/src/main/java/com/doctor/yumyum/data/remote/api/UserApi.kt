package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NicknameCreationService {
    @GET("/api/v1/users/nickname")
    suspend fun getNicknameApi(): Response<GetNicknameResponse>
}

interface NicknameValidationService {
    @GET("/api/v1/users/me/nickname/validation")
    suspend fun validateNicknameApi(
        @Query("nickname") nickname: String
    ):Response<ResponseBody>
}