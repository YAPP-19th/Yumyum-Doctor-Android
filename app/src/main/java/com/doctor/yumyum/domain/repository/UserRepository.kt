package com.doctor.yumyum.domain.repository

import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.response.NicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface UserRepository {
    suspend fun getNickname(): Response<NicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
    suspend fun postFlavor(flavorModel: UserFlavorModel): Response<ResponseBody>
}