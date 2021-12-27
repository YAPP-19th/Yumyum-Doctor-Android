package com.doctor.yumyum.data.remote.api

import com.doctor.yumyum.data.model.UserFlavor
import com.doctor.yumyum.data.remote.response.NicknameResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("/api/v1/users/nickname")
    suspend fun getNickname(): Response<NicknameResponse>

    @GET("/api/v1/users/me/nickname/validation")
    suspend fun validateNickname(
        @Query("nickname") nickname: String
    ): Response<ResponseBody>

    @PATCH("/api/v1/users/me/nickname")
    suspend fun patchNickname(
        @Body nickname: String
    ): Response<ResponseBody>

    @PUT("/api/v1/users/me/flavors")
    suspend fun postFlavor(
        @Body userFlavor: UserFlavor
    ): Response<ResponseBody>

    @GET("/api/v1/users/me")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}