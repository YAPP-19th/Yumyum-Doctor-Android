package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.NicknamePatchModel
import com.doctor.yumyum.data.model.UserFlavorModel
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.response.NicknameResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface UserDataSource {
    suspend fun getNickname(): Response<NicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody>
    suspend fun postFlavor(flavorModel: UserFlavorModel): Response<ResponseBody>
}

class UserDataSourceImpl : UserDataSource {

    override suspend fun getNickname(): Response<NicknameResponse> =
        RetrofitClient.getClient().create(UserService::class.java)
            .getNickname()

    override suspend fun validateNickname(nickname: String): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java)
            .validateNickname(nickname)

    override suspend fun patchNickname(nicknamePatchModel: NicknamePatchModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java)
            .patchNickname(nicknamePatchModel)

    override suspend fun postFlavor(flavorModel: UserFlavorModel): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java).postFlavor(flavorModel)

}
