package com.doctor.yumyum.data.remote.datasource

import com.doctor.yumyum.common.network.RetrofitClient
import com.doctor.yumyum.data.model.Nickname
import com.doctor.yumyum.data.model.UserFlavor
import com.doctor.yumyum.data.remote.api.UserService
import com.doctor.yumyum.data.remote.response.NicknameResponse
import com.doctor.yumyum.data.remote.response.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface UserDataSource {
    suspend fun getNickname(): Response<NicknameResponse>
    suspend fun validateNickname(nickname: String): Response<ResponseBody>
    suspend fun patchNickname(nickname: String): Response<ResponseBody>
    suspend fun postFlavor(flavor: UserFlavor): Response<ResponseBody>
    suspend fun getUserInfo(): Response<UserInfoResponse>
}

class UserDataSourceImpl : UserDataSource {

    override suspend fun getNickname(): Response<NicknameResponse> =
        RetrofitClient.getClient().create(UserService::class.java)
            .getNickname()

    override suspend fun validateNickname(nickname: String): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java)
            .validateNickname(nickname)

    override suspend fun patchNickname(nickname: String): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java)
            .patchNickname(nickname)

    override suspend fun postFlavor(flavor: UserFlavor): Response<ResponseBody> =
        RetrofitClient.getClient().create(UserService::class.java).postFlavor(flavor)

    override suspend fun getUserInfo(): Response<UserInfoResponse> =
        RetrofitClient.getClient().create(UserService::class.java).getUserInfo()

}
