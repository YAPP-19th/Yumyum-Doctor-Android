package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.model.UserFlavor
import com.doctor.yumyum.data.remote.response.NicknameResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface UserRepository {
    suspend fun getNickname(): Response<NicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nickname: String): Response<ResponseBody>
    suspend fun postFlavor(flavor: UserFlavor): Response<ResponseBody>
    suspend fun getUserInfo(): Response<UserInfoResponse>
    suspend fun deleteUser(): Response<ResponseBody>
}