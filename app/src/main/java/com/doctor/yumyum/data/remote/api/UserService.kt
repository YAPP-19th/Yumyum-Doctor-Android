package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.SignUpModel
import com.doctor.yumyum.data.remote.response.GetNicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {

    @GET("/api/v1/users/nickname")
    suspend fun getNickname(): Response<GetNicknameResponse>

    @GET("/api/v1/users/me/nickname/validation")
    suspend fun validateNickname(
        @Query("nickname") nickname: String
    ): Response<ResponseBody>

    @PATCH("/api/v1/users/me/nickname")
    suspend fun patchNickname(
        @Body nicknamePatchModel: NicknamePatchModel
    ): Response<ResponseBody>
}